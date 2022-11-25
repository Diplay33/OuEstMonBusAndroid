package com.example.ouestmonbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ouestmonbus.ui.theme.OùEstMonBusTheme
import kotlinx.coroutines.launch
import model.preferences_data_store.StoreDisplayNotifCountParam
import model.preferences_data_store.StoreFirstLaunch
import view.BottomNavigationBar
import view.Screens.BottomNavigationScreens
import view.Screens.CartesScreens
import view.Screens.PlusScreens
import view.lines_map_list.LinesMapListMain
import view.lines_map_list.line_map.LineMapViewMain
import view.more_view.MoreViewMain
import view.more_view.all_services_list.AllServicesListMain
import view.more_view.all_services_list.service_detail.ServiceDetailMain
import view.next_schedules.NextSchedulesHomeViewMain

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val firstLaunchDataStore = StoreFirstLaunch(context)
            val displayNotifCountDataStore = StoreDisplayNotifCountParam(context)

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
            
            Scaffold(bottomBar = { BottomNavigationBar(navController, bottomNavigationItems) } ) {
                NavHost(
                    navController = navController,
                    startDestination = BottomNavigationScreens.Cartes.route,
                    modifier = Modifier
                        .padding(it)
                ) {
                    //Cartes screen
                    composable(BottomNavigationScreens.Cartes.route) {
                        LinesMapListMain(navController = navController)
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
                        NextSchedulesHomeViewMain()
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
                        route = PlusScreens.ServiceDetail.route + "/{lineId}/{vehicleId}/{destination}/{latitude}/{longitude}/{stationId}/{currentSpeed}/{state}/{stateTime}",
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
                            stateTime = entry.arguments?.getString("stateTime")
                        )
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