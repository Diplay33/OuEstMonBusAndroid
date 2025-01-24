package view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.diplay.ouestmonbus.BuildConfig
import model.preferences_data_store.StoreChosenNetwork
import view.Screens.BottomNavigationScreens
import view.advert_view.AdvertView

@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomNavigationScreens>) {
    val colorScheme = !isSystemInDarkTheme()

    Column {
        BottomNavigation(backgroundColor = if (colorScheme) Color.White else Color(0xff18191A)) {
            val currentRoute = currentRoute(navController)

            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null,
                            tint = if (colorScheme) Color.Black else Color.White
                        )
                    },
                    label = {
                        Text(
                            text = screen.name,
                            color = if (colorScheme) Color.Black else Color.White
                        )
                    },
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