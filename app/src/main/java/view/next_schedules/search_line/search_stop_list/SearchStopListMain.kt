package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.*

@Composable
fun SearchStopListMain(navController: NavController, lineId: String?, pathDirection: String?) {
    val line = Lines.getLine(lineId)
    val paths = remember {
        mutableStateListOf<Path>()
    }
    val destinations = if (pathDirection == "ALLER")
        DestinationsAller.getDestinationAllerOfLine(line.id)
    else
        DestinationsRetour.getDestinationRetourOfLine(line.id)
    val stops = remember {
        mutableStateListOf<Station>()
    }

    LaunchedEffect(lineId) {
        Paths.getOrderedPathsByLine(lineId?.toInt() ?: 0) { returnedPaths ->
            paths.clear()
            returnedPaths.map { if (it.first().direction == pathDirection) paths.addAll(it) }
        }
        Stations.getSortedStationsByLineAndDirection(
            lineId = line.id, 
            direction = pathDirection ?: "ALLER"
        ) { returnedStations ->
            stops.clear()
            stops.addAll(returnedStations)
        }
    }

    Scaffold(topBar = { SearchStopListTopBar(navController) }) { padding ->
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(padding)
        ) {
            SearchStopListHeader(line, paths, destinations)

            Spacer(modifier = Modifier
                .height(30.dp)
            )

            stops.forEach { stop ->
                SearchStopListRow(stop, stops, navController, line)
            }
        }
    }
}