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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.Line
import model.DTO.Lines
import model.DTO.Vehicles
import model.preferences_data_store.StoreChosenNetwork

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
    val line = Lines.getLine(lineId?.toInt() ?: 0)
    val vehicle = Vehicles.getVehicle(vehicleId ?: "")
    val colorScheme = !isSystemInDarkTheme()
    val context = LocalContext.current
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
    }

    Scaffold(topBar = { ServiceDetailTopBar(vehicle.parkId, navController) }) { padding ->
        Column(modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(padding)
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            ServiceDetailHeader(line = line, destination ?: "")

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailVehicleRow(vehicle.fullName, line.name)

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailOperatorRow(vehicle.operator)

            vehicle.tciId?.let {
                Spacer(modifier = Modifier
                    .height(10.dp)
                )

                ServiceDetailTCIRow(it)
            }

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailMapRow(
                line = line,
                stationId = stationId ?: "",
                latitude = latitude?.toDouble() ?: 0.0,
                longitude = longitude?.toDouble() ?: 0.0,
                pathId = pathId
            )

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            if(network.value != "filbleu") {
                ServiceDetailSpeedRow(currentSpeed?.toInt() ?: 0)
            }

            if(network.value == "tbm") {
                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                ServiceDetailStateRow(state ?: "", stateTime?.toInt() ?: 0)
            }
        }
    }
}