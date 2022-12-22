package view.lines_map_list.line_map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import com.diplay.ouestmonbus.R
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
                title = service.vehicle.parkId,
                onClick = { marker ->
                    selectedService.value = service
                    marker.showInfoWindow()
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