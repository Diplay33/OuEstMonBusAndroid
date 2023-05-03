package view.next_schedules.search_line.search_stop_list.next_line_schedules

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
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import model.DTO.Station
import model.DTO.Stations
import view.lines_map_list.line_map.MapStyle

@Composable
fun NextLineSchedulesMap(stopId: String?) {
    val colorScheme = !isSystemInDarkTheme()
    val station = remember {
        mutableStateOf<Station?>(null)
    }
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

    LaunchedEffect(stopId) {
        Stations.getStationByStationId(stopId ?: "") {
            scope.launch {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(it.latitude, it.longitude), 12.5f
                )
                station.value = it
            }
        }
    }

    GoogleMap(
        modifier = Modifier
            .height(200.dp)
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp)),
        properties = mapProperties,
        cameraPositionState = cameraPositionState
    ) {

    }
}