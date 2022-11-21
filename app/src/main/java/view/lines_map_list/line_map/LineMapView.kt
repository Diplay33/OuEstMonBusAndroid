package view.lines_map_list.line_map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LineMapView() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(44.838670, -0.578620), 10.8f
        )
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(isBuildingEnabled = true))
    }

    GoogleMap(
        properties = mapProperties,
        cameraPositionState = cameraPositionState
    )
}