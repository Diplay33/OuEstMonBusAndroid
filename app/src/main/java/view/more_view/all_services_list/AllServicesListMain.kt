package view.more_view.all_services_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Service
import model.DTO.Services
import view.lines_map_list.SearchDisplay
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AllServicesListMain(
    state: AllServicesListSearchState = rememberSearchState(),
    navController: NavController
) {
    val filteredServices = remember {
        mutableStateListOf<List<Service>>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val refreshDate = remember {
        mutableStateOf(Calendar.getInstance().time)
    }
    val formatter = SimpleDateFormat("HH:mm")

    if(filteredServices.isEmpty()) {
        Services.getAllServices {
            filteredServices.clear()
            filteredServices.addAll(Services.filterServicesByVehicle(it))
            isLoading.value = false
            refreshDate.value = Calendar.getInstance().time
        }
    }

    Scaffold(topBar = { AllServicesListTopBar(navController, filteredServices, isLoading, refreshDate) }) {
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
            state.searchResults = Services.filterServicesBySearchText(filteredServices, state.query.text)

            when(state.searchDisplay) {
                SearchDisplay.INITIALRESULTS -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                    ) {
                        var servicesCount = 0
                        state.searchResults.forEach { services ->
                            if(services.isNotEmpty()) {
                                servicesCount += services.size
                                AllServicesListGroup(services, navController)
                            }
                        }

                        if(!isLoading.value) {
                            Text(
                                text = if (servicesCount == 0)
                                    "Aucun véhicule en circulation"
                                else
                                    if (servicesCount == 1)
                                        "1 véhicule en circulation"
                                    else
                                        "$servicesCount véhicules en circulation",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )

                            Text(
                                text = "Dernière actualisation à ${formatter.format(refreshDate.value)}",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier
                            .height(65.dp)
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                    ) {
                        var servicesCount = 0
                        state.searchResults.forEach { services ->
                            if(services.isNotEmpty()) {
                                servicesCount =+ services.size
                                AllServicesListGroup(services, navController)
                            }
                        }

                        if(!isLoading.value) {
                            Text(
                                text = if (servicesCount == 0)
                                    "Aucun véhicule en trouvé"
                                else
                                    if (servicesCount == 1)
                                        "1 véhicule trouvé"
                                    else
                                        "$servicesCount véhicules trouvés",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )

                            Text(
                                text = "Dernière actualisation à ${formatter.format(refreshDate.value)}",
                                color = Color.Gray,
                                fontSize = 18.sp
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