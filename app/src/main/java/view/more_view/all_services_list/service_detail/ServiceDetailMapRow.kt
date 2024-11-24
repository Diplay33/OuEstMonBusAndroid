package view.more_view.all_services_list.service_detail

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.diplay.ouestmonbus.R
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import model.DTO.Line
import model.DTO.Paths
import model.DTO.Station
import model.DTO.Stations
import model.preferences_data_store.StoreChosenNetwork
import view.lines_map_list.line_map.MapStyle

@Composable
fun ServiceDetailMapRow(
    line: Line,
    stationId: String,
    latitude: Double,
    longitude: Double,
    pathId: String?
) {
    val colorScheme = !isSystemInDarkTheme()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude + 0.0005, longitude), 15.5f)
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(
            isBuildingEnabled = true,
            mapStyleOptions = if (colorScheme) null else MapStyleOptions(MapStyle.json)
        ))
    }
    val station = remember {
        mutableStateOf(Station("", "", "", 0.0, 0.0))
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
    val pathCoordinates = remember {
        mutableStateListOf<List<LatLng>>()
    }
    val context = LocalContext.current
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
    }

    LaunchedEffect(network.value) {
        if(network.value.isNotEmpty()) {
            if(stationId == "") {
                station.value = Station("", "", "Arrêt inconnu",  0.0, 0.0)
            }
            else {
                Stations.getStationById(stationId, network.value) { returnedStation ->
                    station.value = returnedStation
                }
            }
        }
        if(network.value == "tbm") {
            Paths.getPath(pathId?.toInt() ?: 0, true) { returnedPath ->
                returnedPath?.let { nonNullPath ->
                    pathCoordinates.addAll(
                        nonNullPath.coordinates.map { coordinates ->
                            coordinates.map { LatLng(it[1], it[0]) }
                        }
                    )
                }
            }
        }
    }

    Box(modifier = Modifier
    ) {
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
                    state = MarkerState(position = LatLng(latitude, longitude)),
                    icon = bitmapDescriptor(LocalContext.current, when(line.name) {
                        "Tram A" -> R.drawable.map_logo_tram
                        "Tram B" -> R.drawable.map_logo_tram
                        "Tram C" -> R.drawable.map_logo_tram
                        "Tram D" -> R.drawable.map_logo_tram
                        "BatCUB" -> R.drawable.map_logo_ferry
                        else -> R.drawable.map_logo_bus
                    })
                )

                pathCoordinates.forEach { coordinates ->
                    Polyline(
                        points = coordinates,
                        color = Color(android.graphics.Color.parseColor(line.colorHex))
                    )
                }
            }
        }

        if(stationId != "0") {
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
}

fun bitmapDescriptor(context: Context, vectorResId: Int): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, 90, 90)
    val bm = Bitmap.createBitmap(90, 90, Bitmap.Config.ARGB_8888)

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}