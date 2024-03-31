package view.next_schedules.search_line.search_stop_list.next_line_schedules.next_schedule_details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Destinations
import model.DTO.Lines
import model.DTO.Service
import model.DTO.Services
import model.DTO.Station
import model.DTO.Stations
import model.DTO.Vehicles
import view.more_view.all_services_list.service_detail.ServiceDetailHeader
import view.more_view.all_services_list.service_detail.ServiceDetailMapRow
import view.more_view.all_services_list.service_detail.ServiceDetailOperatorRow
import view.more_view.all_services_list.service_detail.ServiceDetailSpeedRow
import view.more_view.all_services_list.service_detail.ServiceDetailStateRow
import view.more_view.all_services_list.service_detail.ServiceDetailVehicleRow

@Composable
fun NextScheduleDetailsMain(
    navController: NavController,
    vehicleId: String?,
    stopId: String?,
    stopName: String?,
    lineId: String?
) {
    val vehicle = Vehicles.getVehicleById(vehicleId.toString())
    val line = Lines.getLine(lineId)
    val service = remember {
        mutableStateOf(Service(0, 0, 0, 0, "", 0, "", 0.0, 0.0, 0))
    }

    LaunchedEffect(vehicle) {
        while(true) {
            Services.getServiceByVehicleId(vehicle.id) {
                it?.let { value -> service.value = value }
            }
            delay(5000)
        }
    }

    Scaffold(topBar = { NextScheduleDetailsTopBar(
        navController = navController,
        vehicleParkId = vehicle.parkId
    ) }) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
        ) {
            item {
                NextScheduleDetailsArrivalTimeRow(
                    stopName = stopName ?: "Arrêt inconnu",
                    stopId = stopId ?: "",
                    vehicleId = vehicle.id,
                    navController = navController
                )

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                ServiceDetailHeader(
                    line = line,
                    destination = Destinations.getDestinationFromRaw(
                        destination = service.value.destination,
                        lineId = line.id
                    )
                )

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                ServiceDetailVehicleRow(vehicle.model, line.lineName)

                Spacer(modifier = Modifier
                    .height(10.dp)
                )

                ServiceDetailOperatorRow(vehicle.operator)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                ServiceDetailSpeedRow(service.value.currentSpeed)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                ServiceDetailStateRow(service.value.state, service.value.stateTime)
            }
        }
    }
}