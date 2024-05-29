package view.next_schedules.search_line

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Line
import model.DTO.Lines
import model.DTO.Service
import model.DTO.Services
import view.lines_map_list.LinesMapListSearchBar
import view.lines_map_list.LinesMapListSearchState
import view.lines_map_list.SearchDisplay
import view.lines_map_list.rememberSearchState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchLineViewMain(
    navController: NavController,
    state: LinesMapListSearchState = rememberSearchState()
) {
    val context = LocalContext.current
    val linesBySection = remember {
        mutableStateListOf<List<Line>>()
    }
    val colorScheme = !isSystemInDarkTheme()
    val allServices = remember {
        mutableStateListOf<Service>()
    }
    val areServicesLoading = remember {
        mutableStateOf(true)
    }

    Scaffold(topBar = { SearchLineViewTopBar(navController) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            LinesMapListSearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
                onBack = { state.query = TextFieldValue("") },
                searching = state.searching,
                focused = state.focused
            )

            LaunchedEffect(state.query.text) {
                linesBySection.clear()
                Lines.getAllLinesBySection(context, true) { linesBySection.addAll(it) }
                Services.getAllServices { values ->
                    allServices.clear()
                    allServices.addAll(values)
                    areServicesLoading.value = false
                }

                state.searching = true
                delay(100)
                Lines.getLinesBySearchText(state.query.text) {
                    state.searchResults = it
                }
                state.searching = false
            }

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    Column(modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                    ) {
                        linesBySection.forEach { lines ->
                            SearchLineViewGroup(
                                linesByGroup = linesBySection,
                                lines = lines,
                                isFavorite = linesBySection[0].containsAll(lines) && linesBySection[0].isNotEmpty(),
                                navController = navController,
                                allServices = allServices,
                                areServicesLoading = areServicesLoading.value
                            )
                        }
                    }
                }

                SearchDisplay.NORESULT -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Aucun résultat", textAlign = TextAlign.Center, modifier = Modifier
                            .padding(vertical = 10.dp)
                        )
                    }
                }

                SearchDisplay.RESULTS -> {
                    LazyColumn {
                        items(state.searchResults) { line ->
                            SearchLineViewRow(
                                linesByGroup = linesBySection,
                                line = line,
                                navController = navController,
                                isLineInService = if (areServicesLoading.value)
                                    null
                                else
                                    !allServices.none { it.lineId == line.id }
                            )
                        }

                        item {
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