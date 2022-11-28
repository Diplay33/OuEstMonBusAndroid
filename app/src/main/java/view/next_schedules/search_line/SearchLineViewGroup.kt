package view.next_schedules.search_line

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Line

@Composable
fun SearchLineViewGroup(
    linesByGroup: SnapshotStateList<List<Line>>,
    lines: List<Line>,
    isFavorite: Boolean,
    navController: NavController
) {
    Column(modifier = Modifier
        .padding(vertical = if (lines.isEmpty()) 0.dp else 10.dp)
    ) {
        if(isFavorite && lines.isNotEmpty()) {
            Text("Favoris", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier
                .padding(start = 15.dp)
            )
        }

        lines.forEach { line ->
            if(line.id != 132) {
                SearchLineViewRow(linesByGroup, line, navController)
            }
        }
    }
}