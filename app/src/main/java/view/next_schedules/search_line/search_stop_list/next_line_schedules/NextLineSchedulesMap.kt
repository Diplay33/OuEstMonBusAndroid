package view.next_schedules.search_line.search_stop_list.next_line_schedules

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.DisplayMetrics
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.diplay.ouestmonbus.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import model.DTO.Line
import model.DTO.NSchedulesMapMarker
import model.DTO.NSchedulesMapMarkerType
import model.DTO.Station
import view.lines_map_list.line_map.MapStyle

@Composable
fun NextLineSchedulesMap(station: Station?, line: Line, mapMarkers: List<NSchedulesMapMarker>) {
    val colorScheme = !isSystemInDarkTheme()
    val mapProperties by remember {
        mutableStateOf(MapProperties(
            isBuildingEnabled = true,
            mapStyleOptions = if (colorScheme) null else MapStyleOptions(MapStyle.json)
        ))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(44.838670, -0.578620), 10.8f
        )
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(station) {
        scope.launch {
            station?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(it.latitude, it.longitude), 12.5f
                )
            }
        }
    }

    GoogleMap(
        modifier = Modifier
            .height(210.dp)
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp)),
        properties = mapProperties,
        cameraPositionState = cameraPositionState
    ) {
        mapMarkers.forEach { marker ->
            when(marker.type) {
                NSchedulesMapMarkerType.STOP ->
                    marker.stop?.let { stop ->
                        Marker(
                            state = MarkerState(position = LatLng(stop.latitude, stop.longitude)),
                            icon = setCustomMapStopIcon(stop.name, line.lineColorResource, context),
                            zIndex = 1f
                        )
                    }
                NSchedulesMapMarkerType.VEHICLE ->
                    marker.service?.let { service ->
                        Marker(
                            state = MarkerState(position = LatLng(service.latitude, service.longitude)),
                            icon = setCustomMapServiceIcon(service.vehicle.parkId, colorScheme, context, when(line.lineName) {
                                "Tram A" -> R.drawable.map_logo_tram
                                "Tram B" -> R.drawable.map_logo_tram
                                "Tram C" -> R.drawable.map_logo_tram
                                "Tram D" -> R.drawable.map_logo_tram
                                "BatCUB" -> R.drawable.map_logo_ferry
                                else -> R.drawable.map_logo_bus
                            })
                        )
                    }
            }
        }
    }
}


private fun setCustomMapServiceIcon(parkId: String, colorScheme: Boolean, context: Context, vectorResId: Int): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(if (parkId.length <= 4) 8 else 30, 50, if (parkId.length <= 4) 79 else 101, 121)
    val textPaint = Paint().apply {
        color = if (colorScheme) Color.BLACK else Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = 38.dp.value
        isFakeBoldText = true
    }

    // Mesurez la largeur du texte
    val textWidth = textPaint.measureText(parkId)

    // Créez un bitmap assez grand pour contenir le texte et l'image
    val width = 80.coerceAtLeast(textWidth.toInt()) // Utilisez la largeur maximale entre l'image et le texte
    val height = 120 // Hauteur suffisante pour contenir l'image et le texte

    val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)

    // Dessinez le texte centré horizontalement en haut
    val xText = width / 2f
    val yText = textPaint.textSize // Placez le texte en haut de la canvas
    canvas.drawText(parkId, xText, yText, textPaint)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

private fun setCustomMapStopIcon(message: String, outlineColorResource: Int, context: Context): BitmapDescriptor {
    val height = 125f
    val widthPadding = 50.dp.value
    val width = paintTextWhite.measureText(message, 0, message.length) + widthPadding
    val roundStart = height/3
    val path = Path().apply {
        arcTo(0f, 0f,
            roundStart * 2, roundStart * 2,
            -90f, -180f, true)
        lineTo(width/2 - roundStart / 2, height * 2/3)
        lineTo(width/2, height)
        lineTo(width/2 + roundStart / 2, height * 2/3)
        lineTo(width - roundStart, height * 2/3)
        arcTo(width - roundStart * 2, 0f,
            width, height * 2/3,
            90f, -180f, true)
        lineTo(roundStart, 0f)
    }

    val bm = Bitmap.createBitmap(width.toInt(), height.toInt() , Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)
    canvas.drawPath(path, paintBlackFill)
    canvas.drawPath(path, paintOutline(outlineColorResource, context))
    canvas.drawText(message, width/2, height * 2/3 * 2/3, paintTextWhite)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

val paintTextWhite = Paint().apply {
    strokeCap = Paint.Cap.ROUND
    strokeJoin = Paint.Join.ROUND
    isAntiAlias = true
    color = Color.WHITE
    textAlign = Paint.Align.CENTER
    strokeWidth = 6.dp.value
    textSize = 38.dp.value
}

val paintBlackFill = Paint().apply {
    style = Paint.Style.STROKE
    strokeCap = Paint.Cap.ROUND
    strokeJoin = Paint.Join.ROUND
    isAntiAlias = true
    color = Color.DKGRAY
    style = Paint.Style.FILL
    textAlign = Paint.Align.CENTER
    textSize = 5.dp.value
}

fun paintOutline(colorResource: Int, context: Context): Paint {
    val convertedColor = ContextCompat.getColor(context, colorResource)
    return Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
        color = convertedColor
        strokeWidth = 2.dp.value
    }
}