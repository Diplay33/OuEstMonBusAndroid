package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Line
import model.DTO.Station
import view.Screens.ProchainsScreens

@Composable
fun SearchStopListRow(
    stop: Station,
    stops: List<Station>,
    navController: NavController,
    line: Line?,
    pathDirection: String
) {
    val colorScheme = !isSystemInDarkTheme()

    Box {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(bottom = 10.dp)
            .height(45.dp)
            .clickable {
                line?.let {
                    navController.navigate(
                        ProchainsScreens.NextLineSchedules.withArgs(
                            stop.name,
                            stop.stationId,
                            line.id.toString(),
                            pathDirection
                        )
                    )
                }
            }
            .background(
                if (colorScheme) Color.Transparent else Color(0xff18191A),
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                Color(android.graphics.Color.parseColor(line?.colorHex)).copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp)
                .padding(end = 15.dp)
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = stop.name,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .align(Alignment.CenterVertically)
                )

                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }

        Row(modifier = Modifier
            .padding(start = 38.dp)
            .height(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .align(Alignment.CenterVertically)
            ) {
                if(stops.size > 1) {
                    when(stop) {
                        stops.first() -> {
                            Box(modifier = Modifier
                                .padding(top = 25.dp)
                                .clip(RectangleShape)
                                .background(Color(android.graphics.Color.parseColor(line?.colorHex)))
                                .fillMaxHeight()
                                .width(8.dp)
                            )
                        }
                        stops.last() -> {
                            Box(modifier = Modifier
                                .padding(bottom = 30.dp)
                                .clip(RectangleShape)
                                .background(Color(android.graphics.Color.parseColor(line?.colorHex)))
                                .fillMaxHeight()
                                .width(8.dp)
                            )
                        }
                        else -> {
                            Box(modifier = Modifier
                                .clip(RectangleShape)
                                .background(Color(android.graphics.Color.parseColor(line?.colorHex)))
                                .fillMaxHeight()
                                .width(8.dp)
                            )
                        }
                    }
                }

                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clip(CircleShape)
                    .background(Color(android.graphics.Color.parseColor(line?.colorHex)))
                    .size(20.dp)
                ) {
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .background(if (colorScheme) Color.White else Color.Black)
                        .size(15.dp)
                    )
                }
            }
        }
    }
}