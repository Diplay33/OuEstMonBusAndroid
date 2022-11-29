package view.lines_map_list.line_map.service_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ouestmonbus.R
import model.DTO.Station
import model.DTO.Stations

@Composable
fun LineMapViewServiceDetailCurrentStopRow(stationId: String) {
    val station = remember {
        mutableStateOf(Station(id = 0, stationId = "", name = "", latitude = 0.0, longitude = 0.0))
    }

    LaunchedEffect(stationId) {
        Stations.getStationById(stationId) {
            station.value = it
        }
    }

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            Color(0xffF5F5F5),
            shape = RoundedCornerShape(10.dp)
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