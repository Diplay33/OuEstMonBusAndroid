package view.next_schedules.search_line.search_stop_list.next_line_schedules.line_schedules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.*
import java.util.*

@Composable
fun LineSchedulesMain(
    navController: NavController,
    stationId: String?,
    stationName: String?,
    lineId: String?,
    direction: String?
) {
    val paths = remember {
        mutableStateListOf<Path>()
    }
    val schedules = remember {
        mutableStateListOf<Schedule>()
    }
    val sortedSchedules = Schedules.sortSchedulesByHour(schedules).filter { it.isNotEmpty() }
    val line = Lines.getLine(lineId)
    val collapsedGroupHandler: MutableList<Boolean> = sortedSchedules.map {
        false
    }.toMutableList()
    val isLoading = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(stationId) {
        Paths.getOrderedAllPathsByLine(lineId?.toInt() ?: 0) { returnedPaths ->
            paths.clear()
            returnedPaths.map { value ->
                if(value.first().direction == direction) {
                    paths.addAll(value)

                    Schedules.getSchedulesByStationAndPaths(stationId ?: "", value) {
                        schedules.addAll(it)
                        isLoading.value = false
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = { LineSchedulesTopBar(navController, stationName ?: "Arrêt inconnu") }
    ) { padding ->
        if(isLoading.value) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier
                    .height(10.dp)
                )

                Text(
                    text = "Chargement en cours...",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        else {
            if(schedules.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Aucun résultat à l'heure actuelle",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
            else {
                LazyColumn(modifier = Modifier
                    .padding(padding)
                ) {
                    items(sortedSchedules.size) {groupIndex ->
                        LineSchedulesGroup(
                            schedules = sortedSchedules[groupIndex],
                            line = line,
                            paths = paths,
                            collapsedGroupHandler = collapsedGroupHandler,
                            groupIndex = groupIndex
                        )

                        if(sortedSchedules[groupIndex] != sortedSchedules.last()) {
                            Spacer(modifier = Modifier
                                .height(30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}