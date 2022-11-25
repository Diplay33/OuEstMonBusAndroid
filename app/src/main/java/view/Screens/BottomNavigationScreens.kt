package view.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(val name: String, val route: String, val icon: ImageVector) {
    object Cartes: BottomNavigationScreens("Cartes", "lines_map_list", Icons.Rounded.List)
    object Prochains: BottomNavigationScreens("Proch. passages", "next_schedules_home", Icons.Rounded.DateRange)
    object Plus: BottomNavigationScreens("Plus", "more", Icons.Rounded.MoreVert)
}