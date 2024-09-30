package view.lines_map_list.line_map.service_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.BuildConfig
import model.DTO.Line
import model.DTO.Service
import model.preferences_data_store.StoreChosenNetwork
import view.more_view.all_services_list.service_detail.*
import java.util.Date

@Composable
fun LineMapViewServiceDetail(selectedService: MutableState<Service?>, line: Line?) {
    val service = selectedService.value ?: Service(
        id = 0,
        vehicleId = 0,
        lineId = 0,
        currentSpeed = 0,
        state = "",
        stateTime = 0,
        destination = "",
        latitude = 0.0,
        longitude = 0.0,
        currentStop = 0,
        path = 0,
        timestamp = Date()
    )
    val colorScheme = !isSystemInDarkTheme()
    val context = LocalContext.current
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = "Véhicule n°${service.vehicle.parkId}",
            fontSize = 25.sp,
            color = if (colorScheme) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically)
        )

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(end = 15.dp)
            .clip(CircleShape)
            .background(if (colorScheme) Color.LightGray else Color(0xff18191A))
            .size(35.dp)
            .clickable {
                selectedService.value = null
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White
            )
        }
    }

    Spacer(modifier = Modifier
        .height(10.dp)
    )

    LazyColumn {
        item {
            ServiceDetailHeader(line, service.destination)

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailVehicleRow(service.vehicle.fullName, line?.name ?: "")

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailOperatorRow(service.vehicle.operator)

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            service.vehicle.tciId?.let {
                ServiceDetailTCIRow(it)

                Spacer(modifier = Modifier
                    .height(10.dp)
                )
            }

            if(network.value == "tbm") {
                LineMapViewServiceDetailCurrentStopRow(service.currentStop.toString())

                Spacer(modifier = Modifier
                    .height(10.dp)
                )
            }

            ServiceDetailSpeedRow(service.currentSpeed)

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailStateRow(service.state, service.stateTime)

            Spacer(modifier = Modifier
                .height((if (BuildConfig.DEBUG) 20 else 60).dp)
            )
        }
    }
}