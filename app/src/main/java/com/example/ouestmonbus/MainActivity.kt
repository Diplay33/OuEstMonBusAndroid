package com.example.ouestmonbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ouestmonbus.ui.theme.OùEstMonBusTheme
import view.BottomNavigationBar
import view.Screens.BottomNavigationScreens
import view.Screens.CartesScreens
import view.Screens.PlusScreens
import view.lines_map_list.LinesMapListMain
import view.lines_map_list.line_map.HelloWorld
import view.more_view.MoreViewMain
import view.more_view.all_services_list.AllServicesListMain

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val bottomNavigationItems = listOf(
                BottomNavigationScreens.Cartes,
                BottomNavigationScreens.Plus
            )
            
            Scaffold(bottomBar = { BottomNavigationBar(navController, bottomNavigationItems) } ) {
                NavHost(navController, startDestination = BottomNavigationScreens.Cartes.route) {
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
                        HelloWorld(entry.arguments?.getString("lineId"))
                    }


                    //Plus screen
                    composable(BottomNavigationScreens.Plus.route) {
                        MoreViewMain(navController)
                    }

                    composable(PlusScreens.AllServicesList.route) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            AllServicesListMain(navController = navController)
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