package view.more_view.all_services_list.service_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.Destinations
import model.DTO.Lines
import model.DTO.Vehicles

@Composable
fun ServiceDetailMain(
    navController: NavController,
    lineId: String?,
    vehicleId: String?,
    destination: String?
) {
    val line = Lines.getLine(lineId)
    val vehicle = Vehicles.getVehicleById(vehicleId ?: "")

    Scaffold(topBar = { ServiceDetailTopBar(vehicle.parkId, navController) }) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
        ) {
            ServiceDetailHeader(
                line = line,
                destination = Destinations.getDestinationFromRaw(destination ?: "")
            )

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            ServiceDetailVehicleRow(vehicle.model, line.lineName)

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            ServiceDetailOperatorRow(vehicle.operator)
        }
    }
}