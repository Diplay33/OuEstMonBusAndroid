package view.more_view.all_services_list.service_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.Destinations
import model.DTO.Line
import model.DTO.Lines
import model.DTO.Vehicles

@Composable
fun ServiceDetailMain(
    navController: NavController,
    lineId: String?,
    vehicleId: String?,
    destination: String?,
    stationId: String?,
    latitude: String?,
    longitude: String?,
    currentSpeed: String?,
    state: String?,
    stateTime: String?,
    pathId: String?
) {
    val line = remember {
        mutableStateOf<Line?>(null)
    }
    val vehicle = Vehicles.getVehicleById(vehicleId ?: "")
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(lineId) {
        Lines.getLine(lineId?.toInt() ?: 0) { line.value = it }
    }

    Scaffold(topBar = { ServiceDetailTopBar(vehicle.parkId, navController) }) { padding ->
        Column(modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(padding)
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            ServiceDetailHeader(
                line = line.value,
                destination = Destinations.getDestinationFromRaw(destination ?: "", line.value?.id ?: 0)
            )

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailVehicleRow(vehicle.model, line.value?.name ?: "")

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailOperatorRow(vehicle.operator)

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailMapRow(
                line = line.value,
                stationId = stationId ?: "",
                latitude = latitude?.toDouble() ?: 0.0,
                longitude = longitude?.toDouble() ?: 0.0,
                pathId = pathId
            )

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailSpeedRow(currentSpeed?.toInt() ?: 0)

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailStateRow(state ?: "", stateTime?.toInt() ?: 0)
        }
    }
}