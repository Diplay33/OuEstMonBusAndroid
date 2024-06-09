package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.NextSchedule
import model.DTO.NextSchedulesDestinations
import com.diplay.ouestmonbus.R
import kotlinx.coroutines.delay
import model.DTO.Line

@Composable
fun NextLineSchedulesView(
    nextSchedules: List<NextSchedule>,
    line: Line?,
    isLoading: Boolean,
    focusedVehicle: MutableState<Int?>
) {
    val colorScheme = !isSystemInDarkTheme()

    Text(
        text = "Prochains passages",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = if (colorScheme) Color.Black else Color.White,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(bottom = 3.dp)
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
                text = "Chargement en cours",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
    else {
        if(nextSchedules.none { it.lineId == line?.id }) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(
                    text = "Aucun résultat à l'heure actuelle",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
        else {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .background(
                    if (colorScheme) Color.Transparent else Color(0xff18191A),
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    if (line == null)
                        Color.Transparent
                    else
                        Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(10.dp)
                )
            ) {
                nextSchedules.forEach { nextSchedule ->
                    if(nextSchedule.getTimeLeft().toInt() >= 0) {
                        NextLineSchedulesViewRow(nextSchedule, focusedVehicle, line, nextSchedules)
                    }
                }
            }
        }
    }
}