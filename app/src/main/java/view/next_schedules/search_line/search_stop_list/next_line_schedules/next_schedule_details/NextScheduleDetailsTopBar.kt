package view.next_schedules.search_line.search_stop_list.next_line_schedules.next_schedule_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NextScheduleDetailsTopBar(navController: NavController, vehicleParkId: String) {
    val colorScheme = !isSystemInDarkTheme()

    TopAppBar(
        backgroundColor = if (colorScheme) Color.White else Color.Black,
        elevation = 0.dp
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Véhicule n°$vehicleParkId",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = if (colorScheme) Color.Black else Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )
            }
        }
    }
}