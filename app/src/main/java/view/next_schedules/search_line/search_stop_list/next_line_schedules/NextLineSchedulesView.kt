package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Line
import model.DTO.NextSchedule
import model.DTO.NextSchedulesDestinations
import com.diplay.ouestmonbus.R
import kotlinx.coroutines.delay

@Composable
fun NextLineSchedulesView(nextSchedules: List<NextSchedule>, line: Line, isLoading: Boolean) {
    val animationState = remember {
        mutableStateOf(false)
    }
    val indicatorOpacityState = if (animationState.value) 0.1f else 0.8f
    val indicatorOpacity by animateFloatAsState(targetValue = indicatorOpacityState)

    LaunchedEffect(line) {
        delay(1000)
        while(true) {
            animationState.value = !animationState.value
            delay(1000)
        }
    }

    Text(
        text = "Prochains passages",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(horizontal = 15.dp)
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
        if(nextSchedules.none { it.lineId == line.id }) {
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
                    colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(10.dp)
                )
            ) {
                nextSchedules.forEach { nextSchedule ->
                    val destination = NextSchedulesDestinations.getDestinationFromRaw((nextSchedule.destination))
                    val displayedTime = nextSchedule.getTimeLeft()

                    if(nextSchedule.lineId == line.id && nextSchedule.getTimeLeft().toInt() >= 0) {
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

                            Box(contentAlignment = Alignment.Center, modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(35.dp)
                                .background(
                                    colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(10.dp)
                                )
                            ) {
                                if(displayedTime == "0") {
                                    Text(
                                        text = "PROCHE",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                else {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                                        .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = displayedTime,
                                            fontSize = 15.sp
                                        )

                                        Text(
                                            text = "MIN",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .offset(y = (-3).dp)
                                        )
                                    }
                                }

                                if(nextSchedule.isOnline) {
                                    Image(
                                        painter = painterResource(id = R.drawable.bean_small),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(15.dp)
                                            .offset(x = 11.dp, y = (-11).dp)
                                            .alpha(indicatorOpacity)
                                    )
                                }
                            }
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
    }
}