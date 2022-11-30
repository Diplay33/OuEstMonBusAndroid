package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import model.DTO.Line
import model.DTO.Station
import view.Screens.ProchainsScreens

@Composable
fun SearchStopListRow(stop: Station, stops: List<Station>, navController: NavController, line: Line) {
    Box {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(bottom = 10.dp)
            .height(45.dp)
            .clickable {
                navController.navigate(
                    ProchainsScreens.NextLineSchedules.withArgs(
                        stop.name,
                        stop.stationId,
                        line.id.toString()
                    )
                )
            }
            .background(
                colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .padding(start = 60.dp)
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = stop.name,
                    fontSize = 18.sp
                )
            }
        }

        Row(modifier = Modifier
            .padding(start = 38.dp)
            .height(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if(stop == stops.first()) {
                    Box(modifier = Modifier
                        .padding(top = 25.dp)
                        .clip(RectangleShape)
                        .background(colorResource(id = line.lineColorResource))
                        .fillMaxHeight()
                        .width(8.dp)
                    )
                }
                else if(stop == stops.last()) {
                    Box(modifier = Modifier
                        .padding(bottom = 30.dp)
                        .clip(RectangleShape)
                        .background(colorResource(id = line.lineColorResource))
                        .fillMaxHeight()
                        .width(8.dp)
                    )
                }
                else {
                    Box(modifier = Modifier
                        .clip(RectangleShape)
                        .background(colorResource(id = line.lineColorResource))
                        .fillMaxHeight()
                        .width(8.dp)
                    )
                }

                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = line.lineColorResource))
                    .size(20.dp)
                ) {
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .size(15.dp)
                    )
                }
            }
        }
    }
}