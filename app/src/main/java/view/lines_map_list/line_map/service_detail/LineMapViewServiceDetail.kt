package view.lines_map_list.line_map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Destinations
import model.DTO.Lines
import model.DTO.Service
import view.lines_map_list.line_map.service_detail.LineMapViewServiceDetailCurrentStopRow
import view.more_view.all_services_list.service_detail.*

@Composable
fun LineMapViewServiceDetail(selectedService: MutableState<Service?>) {
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
        currentStop = 0
    )
    val line = Lines.getLine(service.lineId.toString())

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = "Véhicule n°${service.vehicle.parkId}",
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically)
        )

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(end = 15.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .size(35.dp)
            .clickable {
                selectedService.value = null
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null
            )
        }
    }

    Spacer(modifier = Modifier
        .height(10.dp)
    )

    LazyColumn {
        item {
            ServiceDetailHeader(
                line = Lines.getLine(line.id.toString()),
                destination = Destinations.getDestinationFromRaw(service.destination)
            )

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailVehicleRow(service.vehicle.model, line.lineName)

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailOperatorRow(service.vehicle.operator)

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            LineMapViewServiceDetailCurrentStopRow(service.currentStop.toString())

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailSpeedRow(service.currentSpeed)

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailStateRow(service.state, service.stateTime)

            Spacer(modifier = Modifier
                .height(20.dp)
            )
        }
    }
}