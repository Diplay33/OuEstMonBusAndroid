package view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.diplay.ouestmonbus.BuildConfig
import view.Screens.BottomNavigationScreens
import view.advert_view.AdvertView

@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomNavigationScreens>) {
    Column {
        if(!BuildConfig.DEBUG) {
            AdvertView()
        }

        BottomNavigation(backgroundColor = Color.White) {
            val currentRoute = currentRoute(navController)

            items.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                    label = { Text(screen.name) },
                    selected = currentRoute == screen.route,
                    alwaysShowLabel = true,
                    onClick = {
                        if(currentRoute != screen.route) {
                            navController.navigate(screen.route)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}