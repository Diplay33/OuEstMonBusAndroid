package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import model.DTO.Lines

@Composable
fun SearchStopListMain(navController: NavController, lineId: String?) {
    val line = Lines.getLine(lineId)

    Scaffold(topBar = { SearchStopListTopBar(navController) }) { padding ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            Text(line.lineName)
        }
    }
}