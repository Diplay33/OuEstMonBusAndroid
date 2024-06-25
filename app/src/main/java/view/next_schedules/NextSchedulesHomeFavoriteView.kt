package view.next_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Line
import model.DTO.NextSchedule
import view.next_schedules.search_line.NextSchedulesHomeFavoriteNextSchedule

@Composable
fun NextSchedulesHomeFavoriteView(
    line: Line,
    nextSchedules: List<NextSchedule>,
    isLoading: Boolean
) {
    val capFilteredNextSchedules = mutableListOf<NextSchedule>()
    nextSchedules.forEach { ns ->
        if(capFilteredNextSchedules.size < 5 && ns.lineId == line.id) {
            capFilteredNextSchedules.add(ns)
        }
    }
    val colorScheme = !isSystemInDarkTheme()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
        .background(
            if (colorScheme) Color.White else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
        .background(
            Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        if(isLoading) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
            ) {
                CircularProgressIndicator(modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(vertical = 8.dp)
                    .size(20.dp)
                )
            }
        }
        else {
            if(capFilteredNextSchedules.isEmpty()) {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Aucun résultat à l'heure actuelle",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
            else {
                capFilteredNextSchedules.forEach { nextSchedule ->
                    if(nextSchedule.getTimeLeft().toInt() >= 0) {
                        NextSchedulesHomeFavoriteNextSchedule(
                            nextSchedule = nextSchedule,
                            line = line,
                            capFilteredNextSchedules = capFilteredNextSchedules
                        )
                    }
                }
            }
        }
    }
}