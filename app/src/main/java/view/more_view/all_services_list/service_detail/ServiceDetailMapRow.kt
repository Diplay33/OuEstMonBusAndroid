package view.more_view.all_services_list.service_detail

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ouestmonbus.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import model.DTO.Station
import model.DTO.Stations

@Composable
fun ServiceDetailMapRow(lineName: String, stationId: String, latitude: Double, longitude: Double) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude + 0.0005, longitude), 15.5f)
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(isBuildingEnabled = true))
    }
    val station = remember {
        mutableStateOf(Station(0, "", "", 0.0, 0.0))
    }

    LaunchedEffect(lineName) {
        if(stationId == "") {
            station.value = Station(0, "", "Arrêt inconnu",  0.0, 0.0)
        }
        else {
            Stations.getStationById(stationId) { returnedStation ->
                station.value = returnedStation
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
                    })
                )
            }
        }

        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .height(45.dp)
            .fillMaxWidth()
            .background(
                Color(0xffF5F5F5).copy(alpha = 0.8f),
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
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
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