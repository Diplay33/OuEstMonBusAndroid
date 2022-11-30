package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Line
import model.DTO.Station
import view.Screens.ProchainsScreens

@Composable
fun SearchStopListRow(stop: Station, navController: NavController, line: Line) {
    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(bottom = 10.dp)
        .height(45.dp)
        .clickable {
            navController.navigate(
                ProchainsScreens.NextLineSchedules.withArgs(
                    stop.name,
                    stop.stationId
                ))
        }
        .background(
            colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .align(Alignment.CenterVertically)
        ) {
            Text(
                text = stop.name,
                fontSize = 18.sp
            )
        }
    }
}