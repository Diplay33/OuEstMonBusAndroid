package view.more_view.all_services_list.service_detail

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ouestmonbus.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun ServiceDetailMapRow(lineName: String, latitude: Double, longitude: Double) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude + 0.0005, longitude), 15.5f)
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(isBuildingEnabled = true))
    }

    Row(modifier = Modifier
        .height(150.dp)
        .padding(horizontal = 15.dp)
        .clip(RoundedCornerShape(10.dp))
    ) {
        GoogleMap(
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Marker(
                state = MarkerState(position = LatLng(latitude, longitude)),
                icon = bitmapDescriptor(LocalContext.current, when(lineName) {
                    "Tram A" -> R.drawable.map_logo_tram
                    "Tram B" -> R.drawable.map_logo_tram
                    "Tram C" -> R.drawable.map_logo_tram
                    "Tram D" -> R.drawable.map_logo_tram
                    "BatCUB" -> R.drawable.map_logo_ferry
                    else -> R.drawable.map_logo_bus
                }),
                anchor = Offset(0.5f, 0.5f)
            )
        }
    }
}

fun bitmapDescriptor(context: Context, vectorResId: Int): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, 145, 135)
    val bm = Bitmap.createBitmap(145, 135, Bitmap.Config.ARGB_8888)

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}