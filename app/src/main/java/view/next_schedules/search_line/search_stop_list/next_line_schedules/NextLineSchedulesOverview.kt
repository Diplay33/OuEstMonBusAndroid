package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Path
import model.DTO.Schedule
import model.DTO.Schedules

@Composable
fun NextLineSchedulesOverview(schedules: List<Schedule>) {
    val colorScheme = !isSystemInDarkTheme()

    Text(
        text = "Horaires",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = if (colorScheme) Color.Black else Color.White,
        modifier = Modifier
            .padding(horizontal = 15.dp)
    )

    Column {
        schedules.sortedBy { it.rawAppTime }.forEach { schedule ->
            Text(schedule.pathId.toString() + schedule.rawAppTime)
        }
    }
}