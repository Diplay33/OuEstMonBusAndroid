package view.lines_map_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Lines

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LinesMapListMain(state: SearchState = rememberSearchState(), navController: NavController) {
    Scaffold(topBar = { LinesMapListTopBar() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                state.searching = true
                delay(100)
                state.searchResults = Lines.getLinesBySearchText(state.query.text)
                state.searching = false
            }

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    LazyColumn {
                        items(Lines.getLinesByGroup()) { lines ->
                            LinesMapListGroup(lines, navController)
                        }
                    }
                }

                SearchDisplay.NORESULT -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
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
                            LinesMapListRow(line, navController)
                        }
                    }
                }
            }
        }
    }
}