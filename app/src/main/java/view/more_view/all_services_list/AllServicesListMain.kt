package view.more_view.all_services_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diplay.ouestmonbus.BuildConfig
import kotlinx.coroutines.delay
import model.DTO.Service
import model.DTO.Services
import model.preferences_data_store.StoreChosenNetwork
import view.advert_view.AdvertView
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
        mutableStateOf<ArrayList<List<Service>>>(arrayListOf())
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val refreshDate = remember {
        mutableStateOf(Calendar.getInstance().time)
    }
    val formatter = SimpleDateFormat("HH:mm")
    val colorScheme = !isSystemInDarkTheme()
    val totalServicesCount = remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
    }

    LaunchedEffect(network.value) {
        if(network.value.isNotEmpty()) {
            Services.getAllServices(context, network.value) {
                filteredServices.value.clear()
                filteredServices.value.addAll(Services.filterServicesByVehicle(it))
                isLoading.value = false
                refreshDate.value = Calendar.getInstance().time
                totalServicesCount.intValue = it.count()
            }
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        Scaffold(topBar = { AllServicesListTopBar(navController, filteredServices, isLoading, refreshDate) }) { padding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(if (colorScheme) Color.White else Color.Black)
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

                LaunchedEffect(state.query.text, isLoading.value) {
                    state.searching = true
                    delay(100)
                    state.searching = false
                    Services.filterServicesBySearchText(filteredServices.value, state.query.text) { results ->
                        state.searchResults = results
                    }
                }

                when(state.searchDisplay) {
                    SearchDisplay.INITIALRESULTS -> {
                        val collapsedGroupHandler: MutableList<Boolean> = state.searchResults.map {
                            false
                        }.toMutableList()

                        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            if(state.searchResults.size == collapsedGroupHandler.size) {
                                items(state.searchResults.size) { groupIndex ->
                                    if(state.searchResults[groupIndex].isNotEmpty()) {
                                        AllServicesListGroup(
                                            services = state.searchResults[groupIndex],
                                            navController = navController,
                                            collapsedGroupHandler = collapsedGroupHandler,
                                            groupIndex = groupIndex
                                        )
                                    }
                                }
                            }

                            item {
                                if(!isLoading.value) {
                                    Text(
                                        text = when(totalServicesCount.intValue) {
                                            0 -> "Aucun véhicule en circulation"
                                            1 -> "1 véhicule en circulation"
                                            else -> "${totalServicesCount.value} véhicules en circulation"
                                        },
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
                                    .height((if (BuildConfig.DEBUG) 15 else 65).dp)
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

                    SearchDisplay.RESULTS -> {
                        val collapsedGroupHandler: MutableList<Boolean> = state.searchResults.map {
                            false
                        }.toMutableList()

                        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            var servicesCount = 0
                            state.searchResults.forEach { servicesCount += it.size }
                            items(state.searchResults.size) { groupIndex ->
                                if(state.searchResults[groupIndex].isNotEmpty()) {
                                    AllServicesListGroup(
                                        services = state.searchResults[groupIndex],
                                        navController = navController,
                                        collapsedGroupHandler = collapsedGroupHandler,
                                        groupIndex = groupIndex
                                    )
                                }
                            }

                            item {
                                if(!isLoading.value) {
                                    Text(
                                        text = when(servicesCount) {
                                            0 -> "Aucun véhicule trouvé"
                                            1 -> "1 véhicule trouvé"
                                            else -> "$servicesCount véhicules trouvés"
                                        },
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

        if(!BuildConfig.DEBUG) {
            AdvertView()
        }
    }
}