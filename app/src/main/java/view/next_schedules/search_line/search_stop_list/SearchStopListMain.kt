package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(padding)
        ) {
            SearchStopListHeader(line)
        }
    }
}