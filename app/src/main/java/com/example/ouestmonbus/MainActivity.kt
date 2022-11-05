package com.example.ouestmonbus

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ouestmonbus.ui.theme.OùEstMonBusTheme
import view.lines_map_list.LinesMapListMain
import view.lines_map_list.Screens
import view.lines_map_list.line_map.HelloWorld

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favoriteLines")

suspend fun Context.writeInt(key: String, value: Int) {
    dataStore.edit { pref -> pref[intPreferencesKey(key)] = value }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screens.MapListMain.route) {
                composable(route = Screens.MapListMain.route) {
                    LinesMapListMain(navController = navController)
                }

                composable(route = Screens.HelloWorld.route + "/{lineId}", arguments = listOf(
                    navArgument("lineId") {
                        type = NavType.StringType
                        defaultValue = "0"
                        nullable = true
                    }
                )) { entry ->
                    HelloWorld(entry.arguments?.getString("lineId"))
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