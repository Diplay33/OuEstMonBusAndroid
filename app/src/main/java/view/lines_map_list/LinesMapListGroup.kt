package view.lines_map_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.Line

@Composable
fun LinesMapListGroup(lines: ArrayList<Line>, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        lines.forEach { line ->
            LinesMapListRow(line, navController)
        }
    }
}