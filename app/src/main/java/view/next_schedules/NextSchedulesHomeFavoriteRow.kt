package view.next_schedules

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import model.DTO.Line
import model.DTO.Station

@Composable
fun NextSchedulesHomeFavoriteRow(station: Station, lines: List<Line>) {
    Row {
        Text(text = station.name)
        
        lines.forEach { line ->  
            Text(text = line.lineName)
        }
    }
}