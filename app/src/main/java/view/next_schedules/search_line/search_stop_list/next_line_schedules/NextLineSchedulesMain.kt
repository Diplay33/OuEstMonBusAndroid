package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.*

@Composable
fun NextLineSchedulesMain(
    navController: NavController,
    stopName: String?,
    stopId: String?,
    lineId: String?,
    pathDirection: String?
) {
    val line = Lines.getLine(lineId)
    val nextSchedules = remember {
        mutableStateListOf<NextSchedule>()
    }
    val paths = remember {
        mutableStateListOf<Path>()
    }
    val destinations = if (pathDirection == "ALLER")
        DestinationsAller.getDestinationAllerOfLine(line.id)
    else
        DestinationsRetour.getDestinationRetourOfLine(line.id)

    LaunchedEffect(stopName) {
        Paths.getOrderedPathsByLine(line.id) { returnedPaths ->
            paths.clear()
            returnedPaths.map { if (it.first().direction == pathDirection) paths.addAll(it) }
        }

        while(true) {
            NextSchedules.getNextSchedulesByStationId(stopId ?: "") { returnedNextSchedules ->
                nextSchedules.clear()
                nextSchedules.addAll(returnedNextSchedules)
            }
            delay(10000)
        }
    }
    
    Scaffold(topBar = { NextLineSchedulesTopBar(
        navController = navController,
        stopName = stopName ?: "Arrêt inconnu"
    ) }) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
        ) {
            item {
                NextLineSchedulesHeader(line, paths, destinations)
            }

            items(nextSchedules) { nextSchedule ->
                if(nextSchedule.lineId == line.id) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        Text(text = nextSchedule.destination)

                        Text(
                            text = if (nextSchedule.isOnline)
                                nextSchedule.getEstimatedTimeLeft()
                            else
                                nextSchedule.getTheoricTimeLeft(),
                            fontWeight = if (nextSchedule.isOnline)
                                FontWeight.Bold
                            else
                                FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}