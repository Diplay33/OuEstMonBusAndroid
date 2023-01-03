package view.next_schedules.search_line.search_stop_list.next_line_schedules

import android.icu.util.Calendar
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
    val sortedSchedules: List<List<Schedule>> = Schedules.sortSchedulesByHour(schedules)

    Text(
        text = "Horaires",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = if (colorScheme) Color.Black else Color.White,
        modifier = Modifier
            .padding(horizontal = 15.dp)
    )

    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
    ) {
        sortedSchedules.forEach { schedules ->
            if(schedules.isNotEmpty()) {
                val firstScheduleCalendar = Calendar.getInstance()
                firstScheduleCalendar.time = schedules.first().getTime()

                Row {
                    Text(text = "${firstScheduleCalendar.get(Calendar.HOUR_OF_DAY)}h", modifier = Modifier
                        .padding(end = 20.dp)
                    )

                    LazyRow {
                        items(schedules) { schedule ->
                            val scheduleCalendar = Calendar.getInstance()
                            scheduleCalendar.time = schedule.getTime()
                            val minutes = scheduleCalendar.get(Calendar.MINUTE).toString()

                            Text(
                                text = if (minutes.count() < 2) "0${minutes}" else minutes,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(end = 15.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier
                    .height(25.dp)
                )
            }
        }
    }
}