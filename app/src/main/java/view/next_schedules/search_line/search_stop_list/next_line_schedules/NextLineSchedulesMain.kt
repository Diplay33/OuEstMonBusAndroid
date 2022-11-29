package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NextLineSchedulesMain(navController: NavController, stopName: String?) {
    Scaffold(topBar = { NextLineSchedulesTopBar(
        navController = navController,
        stopName = stopName ?: "Arrêt inconnu"
    ) }) { padding ->

    }
}