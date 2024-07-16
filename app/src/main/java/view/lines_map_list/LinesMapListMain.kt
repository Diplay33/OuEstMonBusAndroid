package view.lines_map_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import model.DTO.*
import model.preferences_data_store.StoreChosenNetwork

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LinesMapListMain(
    state: LinesMapListSearchState = rememberSearchState(),
    navController: NavController,
    refreshLinesAction: String?
) {
    val context = LocalContext.current
    val linesBySection = remember {
        mutableStateListOf<List<Line>>()
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
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
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

            LaunchedEffect(network.value) {
                while(true) {
                    Services.getAllServices(context, network.value, true) {
                        allServices.clear()
                        allServices.addAll(it)
                        isLoading.value = false
                    }
                    delay(30000)
                }
            }

            LaunchedEffect(refreshLinesAction) {
                refreshLinesAction?.let { action ->
                    if(action == "reload") {
                        Lines.getAllLinesBySection(context) {
                            linesBySection.clear()
                            linesBySection.addAll(it)
                        }
                    }
                }
            }

            LaunchedEffect(state.query.text) {
                searchText.value = state.query.text
                index.value = 0
                Lines.getAllLinesBySection(context) { linesBySection.addAll(it) }
                ProgrammedMessages.getAllProgrammedMessages { values ->
                    programmedMessages.clear()
                    programmedMessages.addAll(values)
                }
                state.searching = true
                delay(100)
                Lines.getLinesBySearchText(state.query.text) { state.searchResults = it }
                state.searching = false
            }

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    LazyColumn(modifier = Modifier
                        .background(if (colorScheme) Color.White else Color.Black)
                    ) {
                        items(linesBySection.size) { index ->
                            LinesMapListGroup(
                                lines = linesBySection[index],
                                isFavorite = linesBySection[0].containsAll(linesBySection[index]) && linesBySection[0].isNotEmpty(),
                                linesByGroup = linesBySection,
                                navController = navController,
                                services = allServices,
                                isLoading = isLoading,
                                programmedMessages = programmedMessages
                            )
                        }

                        item {
                            Spacer(modifier = Modifier
                                .height(50.dp)
                            )
                        }
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
                                linesByGroup = linesBySection,
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