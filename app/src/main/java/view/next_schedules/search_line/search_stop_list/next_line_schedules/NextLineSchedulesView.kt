package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
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
import model.DTO.NextSchedule
import model.DTO.NextSchedulesDestinations

@Composable
fun NextLineSchedulesView(nextSchedules: List<NextSchedule>, line: Line) {
    Text(
        text = "Prochains passages",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(horizontal = 15.dp)
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
        .background(
            colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        nextSchedules.forEach { nextSchedule ->
            val destination = NextSchedulesDestinations.getDestinationFromRaw((nextSchedule.destination))

            if(nextSchedule.lineId == line.id) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .offset(x = (-7).dp)
                        )

                        if(destination.isEmpty()) {
                            Text(text = nextSchedule.destination, fontSize = 18.sp, modifier = Modifier
                                .padding(vertical = 8.dp)
                                .align(Alignment.CenterVertically)
                            )
                        }
                        else {
                            Column(modifier = Modifier
                                .padding(vertical = 3.dp)
                            ) {
                                Text(
                                    text = destination.first(),
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .offset(y = 2.dp)
                                )

                                Text(destination.last(), fontSize = 18.sp, modifier = Modifier
                                    .offset(y = (-2).dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = if (nextSchedule.isOnline)
                            nextSchedule.getEstimatedTimeLeft()
                        else
                            nextSchedule.getTheoricTimeLeft(),
                        fontWeight = if (nextSchedule.isOnline)
                            FontWeight.Bold
                        else
                            FontWeight.Normal,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                if(nextSchedule != nextSchedules.last { it.lineId == line.id }) {
                    Row {
                        Spacer(modifier = Modifier
                            .width(39.dp)
                        )

                        Box(modifier = Modifier
                            .clip(RectangleShape)
                            .background(Color.LightGray)
                            .fillMaxWidth()
                            .height(1.dp)
                        )
                    }
                }
            }
        }
    }
}