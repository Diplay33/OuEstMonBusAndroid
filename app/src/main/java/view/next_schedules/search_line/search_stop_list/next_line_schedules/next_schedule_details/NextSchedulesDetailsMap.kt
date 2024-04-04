package view.next_schedules.search_line.search_stop_list.next_line_schedules.next_schedule_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import model.DTO.Lines
import model.DTO.Service
import model.DTO.Station
import model.DTO.Stations
import view.currentRoute
import view.lines_map_list.line_map.MapStyle
import view.more_view.all_services_list.service_detail.bitmapDescriptor

@Composable
fun NextSchedulesDetailsMap(service: Service) {
    val colorScheme = !isSystemInDarkTheme()
    val mapProperties by remember {
        mutableStateOf(MapProperties(
            isBuildingEnabled = true,
            mapStyleOptions = if (colorScheme) null else MapStyleOptions(MapStyle.json)
        ))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(44.837789 + 0.0005, -0.57918), 15.5f)
    }
    val mapUISettings by remember {
        mutableStateOf(MapUiSettings(
            compassEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            scrollGesturesEnabled = false,
            tiltGesturesEnabled = false,
            rotationGesturesEnabled = false
        ))
    }
    val line = Lines.getLine(service.lineId.toString())
    val station = remember {
        mutableStateOf(Station(0, "", "", 0.0, 0.0))
    }
    val currentStationId = remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(service) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(service.latitude + 0.0005, service.longitude), 15.5f)

        if(service.currentStop != currentStationId.intValue) {
            Stations.getStationById(service.currentStop.toString()) { station.value = it }
            currentStationId.intValue = service.currentStop
        }
    }

    Box {
        Row(modifier = Modifier
            .height(150.dp)
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp))
        ) {
            GoogleMap(
                properties = mapProperties,
                cameraPositionState = cameraPositionState,
                uiSettings = mapUISettings,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Marker(
                    state = MarkerState(position = LatLng(service.latitude, service.longitude)),
                    icon = bitmapDescriptor(
                        LocalContext.current, when(line.lineName) {
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

        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .height(45.dp)
            .fillMaxWidth()
            .background(
                Color(if (colorScheme) 0xffF5F5F5 else 0xff18191A).copy(alpha = 0.8f),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 15.dp)
                .align(Alignment.CenterVertically)
            ) {
                if(station.value.name.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                    )
                }
                else {
                    Image(
                        painter = painterResource(id = R.drawable.mappin),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (colorScheme)
                            Color.Black
                        else
                            Color.White),
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier
                        .width(15.dp)
                    )

                    Text(
                        text = station.value.name,
                        fontSize = 18.sp,
                        color = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}