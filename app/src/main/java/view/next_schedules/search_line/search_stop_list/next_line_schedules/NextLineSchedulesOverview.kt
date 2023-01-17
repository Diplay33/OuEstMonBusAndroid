package view.next_schedules.search_line.search_stop_list.next_line_schedules

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Line
import model.DTO.Path
import model.DTO.Schedule
import model.DTO.Schedules

@Composable
fun NextLineSchedulesOverview(line: Line, schedules: List<Schedule>, isLoading: Boolean) {
    val colorScheme = !isSystemInDarkTheme()
    val sortedSchedules: List<List<Schedule>> = Schedules.sortSchedulesByHour(schedules)
    val filteredSchedules = sortedSchedules.filter { it.isNotEmpty() }

    Text(
        text = "Horaires",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = if (colorScheme) Color.Black else Color.White,
        modifier = Modifier
            .padding(horizontal = 15.dp)
    )

    if(isLoading) {
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            CircularProgressIndicator(modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier
                .height(10.dp)
            )

            Text(
                text = "Chargement des horaires",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
    else {
        if(schedules.isEmpty()) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(
                    text = "Aucun résultat à ce jour",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
        else {
            //paths
            /*
            Column(modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .background(
                    colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(10.dp)
                )
            ) {
                filteredSchedules.forEach { schedules ->
                    if(schedules.isNotEmpty()) {
                        val firstScheduleCalendar = Calendar.getInstance()
                        firstScheduleCalendar.time = schedules.first().getTime()
                        var shape = RectangleShape
                        if(filteredSchedules.size == 1) {
                            shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                        }
                        else if(schedules == filteredSchedules.first()) {
                            shape = RoundedCornerShape(topStart = 10.dp)
                        }
                        else if(schedules == filteredSchedules.last()) {
                            shape = RoundedCornerShape(bottomStart = 10.dp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .background(
                                        colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                                        shape = shape
                                    )
                            ) {
                                Text(
                                    text = "${firstScheduleCalendar.get(Calendar.HOUR_OF_DAY)}h",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }

                            LazyRow {
                                items(schedules) { schedule ->
                                    val scheduleCalendar = Calendar.getInstance()
                                    scheduleCalendar.time = schedule.getTime()
                                    val minutes = scheduleCalendar.get(Calendar.MINUTE).toString()

                                    Box(contentAlignment = Alignment.Center, modifier = Modifier
                                        .padding(start = 10.dp)
                                        .height(35.dp)
                                        .width(35.dp)
                                        .background(
                                            colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                    ) {
                                        Text(
                                            text = if (minutes.count() < 2) "0${minutes}" else minutes,
                                            fontSize = 18.sp
                                        )
                                    }
                                }

                                item {
                                    Spacer(modifier = Modifier
                                        .width(10.dp)
                                    )
                                }
                            }
                        }

                        if(schedules != filteredSchedules.last()) {
                            Box(modifier = Modifier
                                .clip(RectangleShape)
                                .background(if (colorScheme)
                                    Color.LightGray
                                else
                                    Color.Gray
                                )
                                .fillMaxWidth()
                                .height(1.dp)
                            )
                        }
                    }
                }
            }*/
        }
    }
}