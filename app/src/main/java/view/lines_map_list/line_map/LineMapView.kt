package view.lines_map_list.line_map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.diplay.ouestmonbus.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import model.DTO.Service
import view.more_view.all_services_list.service_detail.bitmapDescriptor

@Composable
fun LineMapView(
    services: SnapshotStateList<Service>,
    lineName: String,
    selectedService: MutableState<Service?>,
    cameraPositionState: CameraPositionState,
    isUserLocationShown: Boolean,
    userPosition: LatLng
) {
    val colorScheme = !isSystemInDarkTheme()
    val mapProperties by remember {
        mutableStateOf(MapProperties(
            isBuildingEnabled = true,
            mapStyleOptions = if (colorScheme) null else MapStyleOptions(MapStyle.json)
        ))
    }
    val mapUISettings by remember {
        mutableStateOf(MapUiSettings(compassEnabled = false, zoomControlsEnabled = false))
    }

    GoogleMap(
        properties = mapProperties,
        cameraPositionState = cameraPositionState,
        uiSettings = mapUISettings
    ) {
        services.forEach { service ->
            Marker(
                state = MarkerState(position = LatLng(service.latitude, service.longitude)),
                icon = setCustomMapServiceIcon(service.vehicle.parkId, colorScheme, LocalContext.current, vectorResId = when(lineName) {
                    "Tram A" -> R.drawable.map_logo_tram
                    "Tram B" -> R.drawable.map_logo_tram
                    "Tram C" -> R.drawable.map_logo_tram
                    "Tram D" -> R.drawable.map_logo_tram
                    "BatCUB" -> R.drawable.map_logo_ferry
                    else -> R.drawable.map_logo_bus
                }),
                onClick = {
                    selectedService.value = service
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(service.latitude - 0.013, service.longitude), 13f
                    )
                    true
                }
            )
        }

        if(isUserLocationShown) {
            Marker(
                state = MarkerState(position = userPosition),
                title = "Ma position"
            )
        }
    }
}

private fun setCustomMapServiceIcon(parkId: String, colorScheme: Boolean, context: Context, vectorResId: Int): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(if (parkId.length <= 4) 7 else 30, 50, if (parkId.length <= 4) 78 else 101, 121)
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