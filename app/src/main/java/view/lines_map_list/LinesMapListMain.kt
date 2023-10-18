package view.lines_map_list

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
import model.DTO.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LinesMapListMain(state: LinesMapListSearchState = rememberSearchState(), navController: NavController) {
    val context = LocalContext.current
    val linesByGroup = remember {
        mutableStateListOf<ArrayList<Line>>()
    }
    val colorScheme = !isSystemInDarkTheme()
    val allServices = remember {
        mutableStateListOf<Service>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val programmedMessages = remember {
        mutableStateListOf<ProgrammedMessage>()
    }
    val searchText = remember {
        mutableStateOf("")
    }
    val index = remember {
        mutableStateOf(0)
    }

    Scaffold(topBar = { LinesMapListTopBar() }) { padding ->
        Column(
            modifier = Modifier
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

            LaunchedEffect("") {
                while(true) {
                    Services.getAllServices {
                        allServices.clear()
                        allServices.addAll(it)
                        isLoading.value = false
                    }
                    delay(30000)
                }
            }

            LaunchedEffect(state.query.text) {
                searchText.value = state.query.text
                index.value = 0
                linesByGroup.addAll(Lines.getLinesByGroup(context))
                ProgrammedMessages.getAllProgrammedMessages { values ->
                    programmedMessages.clear()
                    programmedMessages.addAll(values)
                }
                state.searching = true
                delay(100)
                state.searchResults = Lines.getLinesBySearchText(state.query.text)
                state.searching = false
            }

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    Column(modifier = Modifier
                        .background(if (colorScheme) Color.White else Color.Black)
                        .verticalScroll(rememberScrollState())
                    ) {
                        linesByGroup.forEach { lines ->
                            LinesMapListGroup(
                                lines = lines,
                                isFavorite = linesByGroup[0].containsAll(lines) && linesByGroup[0].isNotEmpty(),
                                linesByGroup = linesByGroup,
                                navController = navController,
                                services = allServices,
                                isLoading = isLoading,
                                programmedMessages = programmedMessages
                            )
                        }

                        Spacer(modifier = Modifier
                            .height(50.dp)
                        )
                    }
                }

                SearchDisplay.NORESULT -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
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
                    LazyColumn {
                        items(state.searchResults) { line ->
                            LinesMapListRow(
                                rowLine = line,
                                linesByGroup = linesByGroup,
                                navController = navController,
                                services = allServices,
                                isLoading = isLoading,
                                programmedMessagesCount = programmedMessages
                                    .filter { it.lineId == line.id }
                                    .size,
                                clickRowLine = false,
                                searchText = searchText,
                                index = index.value
                            )

                            index.value += 1
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