package view.lines_map_list.line_map.messages_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.DTO.Line

@Composable
fun LineMapViewProgrammedMessagesViewMain(line: Line) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = "Messages " + when(line.lineName) {
                "BatCUB", "Tram A", "Tram B", "Tram C", "Tram D" -> "du ${line.lineName}"
                else -> "de la ${line.lineName}"
            }
        )
    }
}