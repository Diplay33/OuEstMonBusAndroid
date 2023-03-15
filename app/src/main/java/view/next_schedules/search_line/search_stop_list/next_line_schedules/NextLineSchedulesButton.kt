package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diplay.ouestmonbus.R
import model.DTO.Line
import model.DTO.Station
import view.Screens.ProchainsScreens

@Composable
fun NextLineSchedulesButton(
    line: Line,
    navController: NavController,
    stopId: String?,
    stopName: String?,
    direction: String?
) {
    val colorScheme = !isSystemInDarkTheme()

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .background(
                if (colorScheme) Color.Transparent else Color(0xff18191A),
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .height(45.dp)
            .clickable {
                navController.navigate(
                    ProchainsScreens.LineSchedules.withArgs(
                        stopId ?: "",
                        stopName ?: "",
                        line.id.toString(),
                        direction ?: ""
                    )
                )
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.timer),
            contentDescription = null,
            tint = colorResource(id = line.lineColorResource),
            modifier = Modifier
                .size(25.dp)
        )

        Spacer(modifier = Modifier
            .width(10.dp)
        )

        Text(
            text = "Afficher les horaires",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = colorResource(id = line.lineColorResource)
        )
    }
}