package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Line
import model.DTO.NextSchedule

@Composable
fun NextLineSchedulesView(nextSchedules: List<NextSchedule>, line: Line) {
    Text(
        text = "Prochains passages",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(horizontal = 15.dp)
    )

    nextSchedules.forEach { nextSchedule ->
        if(nextSchedule.lineId == line.id) {
            Row(modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(text = nextSchedule.destination)

                Text(
                    text = if (nextSchedule.isOnline)
                        nextSchedule.getEstimatedTimeLeft()
                    else
                        nextSchedule.getTheoricTimeLeft(),
                    fontWeight = if (nextSchedule.isOnline)
                        FontWeight.Bold
                    else
                        FontWeight.Normal
                )
            }
        }
    }
}