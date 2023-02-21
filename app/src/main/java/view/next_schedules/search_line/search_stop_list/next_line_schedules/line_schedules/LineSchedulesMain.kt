package view.next_schedules.search_line.search_stop_list.next_line_schedules.line_schedules

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
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

    LaunchedEffect(stationId) {
        Paths.getOrderedAllPathsByLine(lineId?.toInt() ?: 0) { returnedPaths ->
            paths.clear()
            returnedPaths.map { value ->
                if(value.first().direction == direction) {
                    paths.addAll(value)

                    Schedules.getSchedulesByStationAndPaths(stationId ?: "", value) {
                        schedules.addAll(it)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = { LineSchedulesTopBar(navController, stationName ?: "Arrêt inconnu") }
    ) { padding ->
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