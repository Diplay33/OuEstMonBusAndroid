package view.next_schedules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import model.DTO.Line
import model.DTO.NextSchedule
import model.DTO.NextSchedules
import model.DTO.Station
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesView

@Composable
fun NextSchedulesHomeFavoriteRow(station: Station, lines: List<Line>) {
    val nextSchedules = remember {
        mutableStateListOf<NextSchedule>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(station) {
        while(true) {
            NextSchedules.getNextSchedulesByStationId(station.stationId) { values ->
                nextSchedules.clear()
                nextSchedules.addAll(values)
                isLoading.value = false
            }

            delay(10000)
        }
    }

    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(bottom = 10.dp)
        .fillMaxWidth()
        .background(
            Color(0xffF5F5F5),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Column(modifier = Modifier
            .padding(vertical = 10.dp)
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
            ) {
                Text(
                    text = station.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            Column {
                lines.forEach { line ->
                    Column(modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(
                            colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Row(modifier = Modifier
                            .padding(horizontal = 15.dp)
                        ) {
                            Image(
                                painter = painterResource(id = line.lineImageResource),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                            )

                            Spacer(modifier = Modifier
                                .width(15.dp)
                            )

                            Text(
                                text = line.lineName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                        }

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                            )
                        ) {
                            Column {
                                Spacer(modifier = Modifier
                                    .height(15.dp)
                                )

                                NextSchedulesHomeFavoriteView(line, nextSchedules, isLoading.value)

                                Spacer(modifier = Modifier
                                    .height(15.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}