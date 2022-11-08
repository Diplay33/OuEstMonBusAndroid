package view.more_view.all_services_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Service
import model.DTO.Services
import view.lines_map_list.SearchDisplay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AllServicesListMain(
    state: AllServicesListSearchState = rememberSearchState(),
    navController: NavController
) {
    val services = remember {
        mutableStateListOf<MutableList<Service>>()
    }
    Services.getServicesByVehicle { servicesByVehicle ->
        services.addAll(servicesByVehicle)
    }

    Scaffold(topBar = { AllServicesListTopBar(navController) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AllServicesListSearchBar(
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
                state.searchResults = emptyList() //TODO: implement search algorithm
                state.searching = false
            }

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    LazyColumn {
                        items(services) { services ->
                            AllServicesListGroup(services)
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
                        Text("Aucun résultat", textAlign = TextAlign.Center, modifier = Modifier
                            .padding(vertical = 10.dp)
                        )
                    }
                }

                //TODO: implement
                SearchDisplay.RESULTS -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        Text("Aucun résultat", textAlign = TextAlign.Center, modifier = Modifier
                            .padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}