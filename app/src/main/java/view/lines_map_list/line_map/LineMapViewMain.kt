package view.lines_map_list.line_map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import model.DTO.Lines

@Composable
fun LineMapViewMain(navController: NavController, lineId: String?) {
    val line = Lines.getLine(lineId)

    Scaffold { padding ->
        Box(modifier = Modifier
            .padding(padding)
        ) {
            LineMapView()

            LineMapViewTopBar(navController, line)
        }
    }
}