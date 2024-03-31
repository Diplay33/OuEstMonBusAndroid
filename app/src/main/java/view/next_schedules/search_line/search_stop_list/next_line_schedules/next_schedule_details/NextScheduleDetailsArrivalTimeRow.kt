package view.next_schedules.search_line.search_stop_list.next_line_schedules.next_schedule_details

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DTO.NextSchedule
import model.DTO.NextSchedules
import model.DTO.Vehicles

@Composable
fun NextScheduleDetailsArrivalTimeRow(
    stopName: String,
    stopId: String,
    vehicleId: Int,
    navController: NavController
) {
    val colorScheme = !isSystemInDarkTheme()
    val nextSchedule = remember {
        mutableStateOf<NextSchedule?>(null)
    }
    val scheduleIsLoading = remember {
        mutableStateOf(true)
    }
    val vehicle = Vehicles.getVehicleById(vehicleId.toString())
    val isDismissDialogShown = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(stopName) {
        NextSchedules.getNextSchedulesByStationId(stopId) { nextSchedules ->
            nextSchedules.forEach {
                if(it.vehicleId == vehicleId) {
                    nextSchedule.value = it
                    scheduleIsLoading.value = false
                }
            }
        }

        delay(10000)

        while(true) {
            NextSchedules.getNextSchedulesByStationId(stopId) { nextSchedules ->
                nextSchedules.forEach {
                    if(it.vehicleId == vehicleId) {
                        nextSchedule.value = it
                    }
                }
                if(nextSchedules.isNotEmpty() && !nextSchedules.map { it.vehicleId }.contains(vehicleId)) {
                    isDismissDialogShown.value = true
                }
            }
            delay(10000)
        }
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
        .padding(horizontal = 15.dp)
    ) {
        Row(modifier = Modifier
            .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                tint = if (colorScheme) Color.Black else Color.White,
                contentDescription = null, modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            Text(
                text = stopName,
                fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.8f)
            )
        }

        if(scheduleIsLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        else {
            Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = if (nextSchedule.value?.getTimeLeft()?.toInt() == 0)
                        "proche"
                    else
                        "${nextSchedule.value?.getTimeLeft() ?: 0} min",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (colorScheme) Color.Black else Color.White
                )

                NextScheduleDetailsBeanIndicator()
            }
        }
    }

    if(isDismissDialogShown.value) {
        AlertDialog(
            onDismissRequest = {
                isDismissDialogShown.value = false
                navController.navigateUp()
            },
            title = {
                Text(
                    text = "Information",
                    color = if (colorScheme) Color.Black else Color.White
                )
            },
            text = {
                Text(
                    text = "Le ${vehicle.model} n°${vehicle.parkId} a dépassé l'arrêt $stopName",
                    color = if (colorScheme) Color.Black else Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDismissDialogShown.value = false
                        navController.navigateUp()
                    }
                ) {
                    Text("OK")
                }
            },
            backgroundColor = if (colorScheme) Color.White else Color.Black
        )
    }
}
