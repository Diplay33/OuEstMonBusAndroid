package view.next_schedules.search_line.search_stop_list.next_line_schedules.line_schedules

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun LineSchedulesMain(navController: NavController, stationId: String?, stationName: String?) {
    Scaffold(
        topBar = { LineSchedulesTopBar(navController, stationName ?: "Arrêt inconnu") }
    ) {

    }
}