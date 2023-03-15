package view.lines_map_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Line
import model.DTO.ProgrammedMessage
import model.DTO.Service

@Composable
fun LinesMapListGroup(
    lines: ArrayList<Line>,
    isFavorite: Boolean,
    linesByGroup: SnapshotStateList<ArrayList<Line>>,
    navController: NavController,
    services: MutableList<Service>,
    isLoading: MutableState<Boolean>,
    programmedMessages: List<ProgrammedMessage>
) {
    Column(modifier = Modifier
        .padding(vertical = if(lines.isEmpty()) 0.dp else 10.dp)
    ) {
        if(isFavorite && lines.isNotEmpty()) {
            Text("Favoris", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier
                .padding(start = 15.dp)
            )
        }

        lines.forEach { line ->
            LinesMapListRow(
                rowLine = line,
                linesByGroup = linesByGroup,
                navController = navController,
                services = services,
                isLoading = isLoading,
                programmedMessagesCount = programmedMessages.filter { it.lineId == line.id }.size
            )
        }
    }
}