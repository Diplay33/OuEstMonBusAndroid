package view.next_schedules.search_line.search_stop_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.*
import view.lines_map_list.SearchDisplay
import view.next_schedules.search_line.search_stop_list.next_line_schedules.SearchStopListSearchBar
import view.next_schedules.search_line.search_stop_list.next_line_schedules.SearchStopListSearchState
import view.next_schedules.search_line.search_stop_list.next_line_schedules.rememberSearchState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchStopListMain(
    navController: NavController,
    lineId: String?,
    pathDirection: String?,
    state: SearchStopListSearchState = rememberSearchState()
) {
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
    val colorScheme = !isSystemInDarkTheme()

    Scaffold(topBar = { SearchStopListTopBar(navController) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            SearchStopListSearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                onBack = { state.query = TextFieldValue("") },
                searching = state.searching,
                focused = state.focused
            )

            LaunchedEffect(state.query.text) {
                Paths.getOrderedPathsByLine(lineId?.toInt() ?: 0) { returnedPaths ->
                    paths.clear()
                    returnedPaths.map { if (it.first().direction == pathDirection) paths.addAll(it) }

                    Stations.getSortedStationsByPaths(paths) { returnedStations ->
                        returnedStations.forEach { station ->  
                            if(!stops.map { it.stationId }.contains(station.stationId)) {
                                stops.add(station)
                            }
                        }
                        isLoading.value = false
                    }
                }

                state.searching = true
                delay(100)
                state.searching = false
            }
            state.searchResults = Stations.filterStationsBySearchText(
                stations = stops.sortedBy { it.name },
                searchText = state.query.text
            )

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
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
                            stops.sortedBy { it.name }.forEach { stop ->
                                SearchStopListRow(
                                    stop = stop,
                                    stops = stops.sortedBy { it.name },
                                    navController = navController,
                                    line = line,
                                    pathDirection = pathDirection ?: "ALLER"
                                )
                            }
                        }
                    }
                }

                SearchDisplay.NORESULT -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Aucun résultat",
                            textAlign = TextAlign.Center,
                            color = if (colorScheme) Color.Black else Color.White,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        )
                    }
                }

                SearchDisplay.RESULTS -> {
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
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
                            state.searchResults.forEach { stop ->
                                SearchStopListRow(
                                    stop = stop,
                                    stops = state.searchResults,
                                    navController = navController,
                                    line = line,
                                    pathDirection = pathDirection ?: ""
                                )
                            }

                            Spacer(modifier = Modifier
                                .height(300.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}