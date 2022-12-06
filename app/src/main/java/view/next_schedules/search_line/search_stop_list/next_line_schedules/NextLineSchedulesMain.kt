package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    val isLoading = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(stopName) {
        Paths.getOrderedPathsByLine(line.id) { returnedPaths ->
            paths.clear()
            returnedPaths.map { if (it.first().direction == pathDirection) paths.addAll(it) }
        }

        while(true) {
            NextSchedules.getNextSchedulesByStationId(stopId ?: "") { returnedNextSchedules ->
                nextSchedules.clear()
                nextSchedules.addAll(returnedNextSchedules)
                isLoading.value = false
            }
            delay(10000)
        }
    }
    
    Scaffold(topBar = { NextLineSchedulesTopBar(
        navController = navController,
        stopId = stopId.toString(),
        stopName = stopName ?: "Arrêt inconnu",
        line = line
    ) }) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
        ) {
            item {
                NextLineSchedulesHeader(line, paths, destinations)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextLineSchedulesView(nextSchedules, line, isLoading.value)
            }
        }
    }
}