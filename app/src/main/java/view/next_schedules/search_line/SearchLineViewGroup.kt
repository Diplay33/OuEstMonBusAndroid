package view.next_schedules.search_line

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Line
import model.DTO.Service

@Composable
fun SearchLineViewGroup(
    linesByGroup: SnapshotStateList<List<Line>>,
    lines: List<Line>,
    isFavorite: Boolean,
    navController: NavController,
    allServices: List<Service>,
    areServicesLoading: Boolean
) {
    val colorScheme = !isSystemInDarkTheme()

    Column(modifier = Modifier
        .padding(vertical = if (lines.isEmpty()) 0.dp else 10.dp)
    ) {
        if(isFavorite && lines.isNotEmpty()) {
            Text(
                text = "Favoris",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .padding(start = 15.dp)
            )
        }

        lines.forEach { line ->
            if(line.lineName != "Navette Tram" && line.lineName != "Flex'Artigues") {
                SearchLineViewRow(
                    linesByGroup = linesByGroup,
                    line = line,
                    navController = navController,
                    isLineInService = if (areServicesLoading)
                        null
                    else
                        !allServices.none { it.lineId == line.id }
                )
            }
        }
    }
}