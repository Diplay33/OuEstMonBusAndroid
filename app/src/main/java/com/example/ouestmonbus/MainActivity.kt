package com.diplay.ouestmonbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.diplay.ouestmonbus.ui.theme.OùEstMonBusTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.DTO.AllerDestination
import model.DTO.AllerDestinations
import model.DTO.Destination
import model.DTO.Destinations
import model.DTO.Line
import model.DTO.Lines
import model.DTO.NextSchedulesDestination
import model.DTO.NextSchedulesDestinations
import model.DTO.PathDestination
import model.DTO.PathDestinations
import model.DTO.RetourDestination
import model.DTO.RetourDestinations
import model.DTO.Vehicle
import model.DTO.Vehicles
import model.SupabaseManager
import model.preferences_data_store.StoreChosenNetwork
import model.preferences_data_store.StoreDisplayNotifCountParam
import model.preferences_data_store.StoreFirstLaunch
import view.BottomNavigationBar
import view.Screens.BottomNavigationScreens
import view.Screens.CartesScreens
import view.Screens.PlusScreens
import view.Screens.ProchainsScreens
import view.Screens.SplashScreens
import view.lines_map_list.LinesMapListMain
import view.lines_map_list.line_map.LineMapViewMain
import view.more_view.MoreViewMain
import view.more_view.all_services_list.AllServicesListMain
import view.more_view.all_services_list.service_detail.ServiceDetailMain
import view.next_schedules.NextSchedulesHomeViewMain
import view.next_schedules.favorite_stop_detail.FavoriteStopDetailMain
import view.next_schedules.search_line.SearchLineViewMain
import view.next_schedules.search_line.search_stop_list.SearchStopListMain
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesMain
import view.next_schedules.search_line.search_stop_list.next_line_schedules.line_schedules.LineSchedulesMain
import view.next_schedules.search_line.search_stop_list.next_line_schedules.next_schedule_details.NextScheduleDetailsMain
import view.whats_new_view.WhatsNewViewMain

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val firstLaunchDataStore = StoreFirstLaunch(context)
            val displayNotifCountDataStore = StoreDisplayNotifCountParam(context)
            val chosenNetworkDataStore = StoreChosenNetwork(context)
            val refreshLinesAction = remember {
                mutableStateOf<String?>(null)
            }
            val showSplashScreen = remember {
                mutableStateOf(false)
            }
            val network = remember {
                mutableStateOf<String?>(null)
            }

            if(firstLaunchDataStore.isEnabled.collectAsState(initial = false).value != true) {
                scope.launch {
                    firstLaunchDataStore.enable()
                    displayNotifCountDataStore.enable()
                }
            }

            val navController = rememberNavController()
            val bottomNavigationItems = listOf(
                BottomNavigationScreens.Cartes,
                BottomNavigationScreens.Prochains,
                BottomNavigationScreens.Plus
            )

            val colorScheme = !isSystemInDarkTheme()
            val systemUiController = rememberSystemUiController()
            if(colorScheme){
                systemUiController.setSystemBarsColor(
                    color = Color.White
                )
            }
            else{
                systemUiController.setSystemBarsColor(
                    color = Color(0xff18191A)
                )
            }

            LaunchedEffect(Unit) {
                chosenNetworkDataStore.chosenNetwork.firstOrNull()?.let { returnedNetwork ->
                    network.value = returnedNetwork
                    withContext(Dispatchers.IO) {
                        SupabaseManager.beginSyncDatabaseProcess(context) { refreshLinesAction.value = it }
                    }
                } ?: run {
                    showSplashScreen.value = true
                }
            }

            if(showSplashScreen.value) {
                //Splash screen
                Scaffold { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = SplashScreens.WhatsNew.route,
                        modifier = Modifier
                            .padding(padding)
                    ) {
                        composable(SplashScreens.WhatsNew.route) {
                            WhatsNewViewMain(showSplashScreen, network.value)
                        }
                    }
                }
            }
            else {
                Scaffold(bottomBar = { BottomNavigationBar(navController, bottomNavigationItems) } ) {
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavigationScreens.Cartes.route,
                        enterTransition = { fadeIn(animationSpec = tween(300)) },
                        exitTransition = { scaleOut(targetScale = 1.2f) },
                        popEnterTransition = {
                            fadeOut(animationSpec = tween(300))
                            scaleIn(initialScale = 1.2f) },
                        popExitTransition = { fadeOut(animationSpec = tween(300)) },
                        modifier = Modifier
                            .padding(it)
                    ) {
                        //Cartes screen
                        composable(BottomNavigationScreens.Cartes.route) {
                            LinesMapListMain(
                                navController = navController,
                                refreshLinesAction = refreshLinesAction.value
                            )
                        }

                        composable(route = CartesScreens.HelloWorld.route + "/{lineId}", arguments = listOf(
                            navArgument("lineId") {
                                type = NavType.StringType
                                defaultValue = "0"
                                nullable = true
                            }
                        )) { entry ->
                            LineMapViewMain(navController, entry.arguments?.getString("lineId"))
                        }


                        //BottomNavigationBar
                        composable(BottomNavigationScreens.Plus.route) {
                            MoreViewMain(navController)
                        }


                        //Prochains Passages screen
                        composable(BottomNavigationScreens.Prochains.route) {
                            NextSchedulesHomeViewMain(navController)
                        }

                        composable(
                            route = ProchainsScreens.FavoriteStopDetail.route + "/{stopId}/{stopName}/{lineId}",
                            arguments = listOf(
                                navArgument("stopId") {
                                    type = NavType.StringType
                                    defaultValue = "0"
                                    nullable = true
                                }
                            )
                        ) { entry ->
                            FavoriteStopDetailMain(
                                navController = navController,
                                stopId = entry.arguments?.getString("stopId"),
                                stopName = entry.arguments?.getString("stopName"),
                                lineId = entry.arguments?.getString("lineId")
                            )
                        }

                        composable(ProchainsScreens.SearchLineView.route) {
                            SearchLineViewMain(navController)
                        }

                        composable(
                            route = ProchainsScreens.SearchStopList.route
                                    + "/{lineId}/{pathDirection}",
                            arguments = listOf(
                                navArgument("lineId") {
                                    type = NavType.StringType
                                    defaultValue = "0"
                                    nullable = true
                                }
                            )
                        ) { entry ->
                            SearchStopListMain(
                                navController = navController,
                                lineId = entry.arguments?.getString("lineId"),
                                pathDirection = entry.arguments?.getString("pathDirection")
                            )
                        }

                        composable(
                            route = ProchainsScreens.NextLineSchedules.route + "/{stopName}/{stopId}/{lineId}/{pathDirection}",
                            arguments = listOf(
                                navArgument("stopName") {
                                    type = NavType.StringType
                                    defaultValue = "0"
                                    nullable = true
                                }
                            )
                        ) { entry ->
                            NextLineSchedulesMain(
                                navController = navController,
                                stopName = entry.arguments?.getString("stopName"),
                                stopId = entry.arguments?.getString("stopId"),
                                lineId = entry.arguments?.getString("lineId"),
                                pathDirection = entry.arguments?.getString("pathDirection")
                            )
                        }

                        composable(
                            route = ProchainsScreens.NextScheduleDetails.route + "/{vehicleId}/{stopId}/{stopName}/{lineId}",
                            arguments = listOf()
                        ) { entry ->
                            NextScheduleDetailsMain(
                                navController = navController,
                                vehicleId = entry.arguments?.getString("vehicleId"),
                                stopId = entry.arguments?.getString("stopId"),
                                stopName = entry.arguments?.getString("stopName"),
                                lineId = entry.arguments?.getString("lineId")
                            )
                        }

                        composable(
                            route = ProchainsScreens.LineSchedules.route + "/{stopId}/{stopName}/{lineId}/{direction}/{selectedDate}",
                            arguments = listOf(
                                navArgument("stopId") {
                                    type = NavType.StringType
                                    defaultValue = "0"
                                    nullable = true
                                }
                            )
                        ) { entry ->
                            LineSchedulesMain(
                                navController = navController,
                                stationId = entry.arguments?.getString("stopId"),
                                stationName = entry.arguments?.getString("stopName"),
                                lineId = entry.arguments?.getString("lineId"),
                                direction = entry.arguments?.getString("direction"),
                                selectedDate = entry.arguments?.getString("selectedDate")
                            )
                        }


                        //Plus screen
                        composable(PlusScreens.AllServicesList.route) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                                .fillMaxWidth()
                            ) {
                                AllServicesListMain(navController = navController)
                            }
                        }

                        composable(
                            route = PlusScreens.ServiceDetail.route + "/{lineId}/{vehicleId}/{destination}/{latitude}/{longitude}/{stationId}/{currentSpeed}/{state}/{stateTime}/{pathId}",
                            arguments = listOf(
                                navArgument("lineId") {
                                    type = NavType.StringType
                                    defaultValue = "0"
                                    nullable = true
                                }
                            )
                        ) { entry ->
                            ServiceDetailMain(
                                navController = navController,
                                lineId = entry.arguments?.getString("lineId"),
                                vehicleId = entry.arguments?.getString("vehicleId"),
                                destination = entry.arguments?.getString("destination"),
                                latitude = entry.arguments?.getString("latitude"),
                                longitude = entry.arguments?.getString("longitude"),
                                stationId = entry.arguments?.getString("stationId"),
                                currentSpeed = entry.arguments?.getString("currentSpeed"),
                                state = entry.arguments?.getString("state"),
                                stateTime = entry.arguments?.getString("stateTime"),
                                pathId = entry.arguments?.getString("pathId")
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OùEstMonBusTheme {
        Greeting("Android")
    }
}