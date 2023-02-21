package view.next_schedules.search_line.search_stop_list.next_line_schedules.line_schedules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Line
import model.DTO.Path
import model.DTO.PathDestinations
import model.DTO.Schedule
import java.util.*

@Composable
fun LineSchedulesRow(line: Line, schedule: Schedule, path: Path) {
    val colorScheme = !isSystemInDarkTheme()
    val scheduleCalendar = Calendar.getInstance()
    scheduleCalendar.time = schedule.getTime() ?: Date()
    val minutes = scheduleCalendar.get(Calendar.MINUTE).toString()
    val destination = PathDestinations.getDestinationFromPathName(path.name)

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = line.lineImageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .offset(x = (-7).dp)
            )

            if(destination.isEmpty()) {
                Text(
                    text = path.name,
                    fontSize = 18.sp,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(fraction = 0.8f)
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

                    Text(
                        text = destination[1],
                        fontSize = 18.sp,
                        color = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .offset(y = (-2).dp)
                    )

                    if(destination.last() != "") {
                        Text(
                            text = destination.last(),
                            modifier = Modifier
                                .offset(y = (-4).dp)
                        )
                    }
                }
            }
        }

        Text(
            text = "${scheduleCalendar.get(Calendar.HOUR_OF_DAY)}h${if (minutes.count() < 2) "0${minutes}" else minutes}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}