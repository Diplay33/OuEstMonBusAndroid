package view.next_schedules.search_line.search_stop_list.next_line_schedules.next_schedule_details

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

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
        mutableStateOf(Service(0, 0, 0, 0, "", 0, "", 0.0, 0.0, 0, timestamp = Date()))
    }
    val cal = Calendar.getInstance()

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

                NextSchedulesDetailsMap(service.value)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                ServiceDetailSpeedRow(service.value.currentSpeed)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                ServiceDetailStateRow(service.value.state, service.value.stateTime)

                Spacer(modifier = Modifier
                    .height(15.dp)
                )

                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
                ) {
                    cal.time = service.value.timestamp

                    Text(
                        text = "Horodatage des données : " +
                            "${addZeroToOneDigitNbIfNeeded(cal.get(Calendar.HOUR_OF_DAY))}:" +
                            addZeroToOneDigitNbIfNeeded(cal.get(Calendar.MINUTE)) + ":" +
                            addZeroToOneDigitNbIfNeeded(cal.get(Calendar.SECOND)),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

fun addZeroToOneDigitNbIfNeeded(value: Int): String {
    return if (value.toString().count() == 1) "0$value" else value.toString()
}