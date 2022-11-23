package view.lines_map_list.line_map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.example.ouestmonbus.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import model.DTO.Service
import view.more_view.all_services_list.service_detail.bitmapDescriptor

@Composable
fun LineMapView(services: SnapshotStateList<Service>, lineName: String) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(44.838670 - 0.06, -0.578620), 10.8f
        )
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(isBuildingEnabled = true))
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
                icon = bitmapDescriptor(
                    context = LocalContext.current,
                    vectorResId = when(lineName) {
                        "Tram A" -> R.drawable.map_logo_tram
                        "Tram B" -> R.drawable.map_logo_tram
                        "Tram C" -> R.drawable.map_logo_tram
                        "Tram D" -> R.drawable.map_logo_tram
                        "BatCUB" -> R.drawable.map_logo_ferry
                        else -> R.drawable.map_logo_bus
                    }
                ),
                title = service.vehicle.parkId
            )
        }
    }
}