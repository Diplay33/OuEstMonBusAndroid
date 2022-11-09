package view.more_view.all_services_list

import androidx.compose.animation.ExperimentalAnimationApi
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
        mutableStateListOf<Service>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    Services.getAllServices {
        services.clear()
        services.addAll(Services.filterServicesSortedByVehicle(it))
        isLoading.value = false
    }

    Scaffold(topBar = { AllServicesListTopBar(navController, services, isLoading) }) {
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
                state.searching = false
            }
            state.searchResults = Services.filterServicesBySearchText(services, state.query.text)

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                    ) {
                        Services.filterServicesByVehicle(services).forEach { services ->
                            AllServicesListGroup(services)
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
                        Text("Aucun résultat", textAlign = TextAlign.Center, modifier = Modifier
                            .padding(vertical = 10.dp)
                        )
                    }
                }

                SearchDisplay.RESULTS -> {
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                    ) {
                        Services.filterServicesByVehicle(state.searchResults).forEach { services ->
                            AllServicesListGroup(services)
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