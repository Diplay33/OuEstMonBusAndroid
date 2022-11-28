package view.next_schedules.search_line

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Line
import model.DTO.Lines
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
    val linesByGroup = remember {
        mutableStateListOf<List<Line>>()
    }

    Scaffold(topBar = { SearchLineViewTopBar(navController) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
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
                linesByGroup.clear()
                linesByGroup.addAll(Lines.getLinesByGroup(context))

                state.searching = true
                delay(100)
                state.searchResults = Lines.getLinesBySearchText(state.query.text)
                state.searching = false
            }

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    Column(modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                    ) {
                        linesByGroup.forEach { lines ->
                            SearchLineViewGroup(
                                linesByGroup = linesByGroup,
                                lines = lines,
                                isFavorite = linesByGroup[0].containsAll(lines) && linesByGroup[0].isNotEmpty(),
                                navController = navController
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
                            SearchLineViewRow(linesByGroup, line, navController)
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