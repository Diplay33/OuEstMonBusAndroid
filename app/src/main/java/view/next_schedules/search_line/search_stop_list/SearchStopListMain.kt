package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val isLoading = remember {
        mutableStateOf(true)
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
            isLoading.value = false
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

            if(isLoading.value) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    CircularProgressIndicator(modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier
                        .height(10.dp)
                    )
                    
                    Text(
                        text = "Chargement des arrêts",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            else {
                stops.forEach { stop ->
                    SearchStopListRow(stop, stops, navController, line)
                }
            }
        }
    }
}