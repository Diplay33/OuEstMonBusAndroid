package view.next_schedules.search_line

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.diplay.ouestmonbus.R
import kotlinx.coroutines.delay
import model.DTO.Line
import model.DTO.NextSchedule
import model.DTO.NextSchedulesDestination
import model.DTO.NextSchedulesDestinations

@Composable
fun NextSchedulesHomeFavoriteNextSchedule(
    nextSchedule: NextSchedule,
    line: Line,
    capFilteredNextSchedules: List<NextSchedule>
) {
    val destination = remember {
        mutableStateOf<NextSchedulesDestination?>(null)
    }
    val displayedTime = nextSchedule.getTimeLeft()
    val animationState = remember {
        mutableStateOf(false)
    }
    val indicatorOpacityState = if (animationState.value) 0.1f else 0.8f
    val indicatorOpacity by animateFloatAsState(targetValue = indicatorOpacityState)
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(nextSchedule) {
        NextSchedulesDestinations.getDestination(nextSchedule.destination, line.id) {
            destination.value = it
        }
    }

    LaunchedEffect(line) {
        delay(1000)
        while(true) {
            animationState.value = !animationState.value
            delay(1000)
        }
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .offset(x = (-7).dp)
            )

            if(destination.value == null) {
                Text(
                    text = nextSchedule.destination,
                    fontSize = 18.sp,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            else {
                destination.value?.let { destination ->
                    Column(modifier = Modifier
                        .padding(vertical = 3.dp)
                    ) {
                        Text(
                            text = destination.city,
                            fontSize = 13.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .offset(y = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = destination.destination,
                            fontSize = 18.sp,
                            color = if (colorScheme) Color.Black else Color.White,
                            modifier = Modifier
                                .offset(y = (-2).dp)
                        )
                    }
                }
            }
        }

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .align(Alignment.CenterVertically)
            .size(35.dp)
            .background(
                Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
        ) {
            if(displayedTime == "0") {
                Text(
                    text = "PROCHE",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorScheme) Color.Black else Color.White,
                )
            }
            else {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Text(
                        text = displayedTime,
                        fontSize = 15.sp,
                        color = if (colorScheme) Color.Black else Color.White,
                    )

                    Text(
                        text = "MIN",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .offset(y = (-3).dp)
                    )
                }
            }

            if(nextSchedule.isOnline) {
                Image(
                    painter = painterResource(id = R.drawable.bean_small),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(if (colorScheme)
                        Color.Black
                    else
                        Color.White),
                    modifier = Modifier
                        .size(15.dp)
                        .offset(x = 11.dp, y = (-11).dp)
                        .alpha(indicatorOpacity)
                )
            }
        }
    }

    if(nextSchedule != capFilteredNextSchedules.last()) {
        Row {
            Spacer(modifier = Modifier
                .width(39.dp)
            )

            Box(modifier = Modifier
                .clip(RectangleShape)
                .background(if (colorScheme) Color.LightGray else Color.Gray)
                .fillMaxWidth()
                .height(1.dp)
            )
        }
    }
}