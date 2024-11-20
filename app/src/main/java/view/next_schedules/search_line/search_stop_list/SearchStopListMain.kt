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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val line = Lines.getLine(lineId?.toInt() ?: 0)
    val paths = rememberSaveable {
        mutableStateOf<MutableList<Path>>(mutableListOf())
    }
    val stops = remember {
        mutableStateListOf<Station>()
    val stops = rememberSaveable {
        mutableStateOf<MutableList<Station>>(mutableListOf())
    }
    val isLoading = rememberSaveable {
        mutableStateOf(true)
    }
    val pathDirectionState = rememberSaveable {
        mutableStateOf(pathDirection ?: "")
    }
    val destinations = remember {
        mutableStateListOf<List<String>>()
    }
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(line, pathDirectionState.value) {
        if(pathDirectionState.value == "ALLER") {
            AllerDestinations.getListOfDestinations(line.id) {
                destinations.addAll(it)
            }
        }
        else {
            RetourDestinations.getListOfDestinations(line.id) {
                destinations.addAll(it)
            }
        }
    }

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

            LaunchedEffect(state.query.text, pathDirectionState.value) {
                Paths.getOrderedPathsByLine(lineId?.toInt() ?: 0) { returnedPaths ->
                    paths.value = mutableListOf()
                    returnedPaths.map { if (it.first().direction == pathDirectionState.value) paths.value.addAll(it) }

                    Stations.getSortedStationsByPaths(paths.value) { returnedStations ->
                        returnedStations.forEach { station ->  
                            if(!stops.value.map { it.stationId }.contains(station.stationId)) {
                                stops.value.add(station)
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
                stations = stops.value.sortedBy { it.name },
                searchText = state.query.text
            )

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                    ) {
                        SearchStopListHeader(line, paths.value, destinations, pathDirectionState, isLoading, stops)

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
                            stops.value.sortedBy { it.name }.forEach { stop ->
                                SearchStopListRow(
                                    stop = stop,
                                    stops = stops.value.sortedBy { it.name },
                                    navController = navController,
                                    line = line,
                                    pathDirection = pathDirectionState.value
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
                        SearchStopListHeader(line, paths.value, destinations, pathDirectionState, isLoading, stops)

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
                                    pathDirection = pathDirectionState.value
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