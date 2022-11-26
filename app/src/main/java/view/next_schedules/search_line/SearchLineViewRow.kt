package view.next_schedules.search_line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import model.DTO.Line

@Composable
fun SearchLineViewRow(line: Line) {
    val isCollapsed = remember {
        mutableStateOf(true)
    }
    val currentRotation = remember {
        mutableStateOf(0f)//if (isCollapsed.value) -90f else 0f)
    }
    val rotation = remember {
        Animatable(currentRotation.value)
    }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(vertical = 5.dp)
        .background(
            colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .clickable {
                isCollapsed.value = !isCollapsed.value
                scope.launch {
                    rotation.animateTo(
                        targetValue = if (isCollapsed.value) 0f else 90f,
                        animationSpec = tween(200, easing = LinearEasing)
                    )
                }
            }
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                Row {
                    Image(
                        painter = painterResource(id = line.lineImageResource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                    )

                    Spacer(modifier = Modifier
                        .width(15.dp)
                    )

                    Text(
                        text = line.lineName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .align(Alignment.CenterVertically)
                ) {
                    Box(modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .align(Alignment.Center)
                    )

                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(rotation.value)
                    )
                }

            }
        }

        /*if(!isCollapsed.value) {
            Column(modifier = Modifier
                .background(
                    colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
                .fillMaxWidth()
                .padding(15.dp)
            ) {
                Row(modifier = Modifier
                    .background(
                        Color.White.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {  }
                    .padding(horizontal = 15.dp)
                    .padding(vertical = 2.dp)
                    .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .offset(x = (-7).dp)
                    )

                    Column {
                        //if(destination.first() != "") {
                            Text(
                                text = //if (service.destination == "Les Pins" && service.lineId == 9)
                                    "MARTIGNAS SUR JALLE"
                                //else
                                    /*destination.first()*/,
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .offset(y = /*if (destination.first() == "") 0.dp else */2.dp)
                            )
                        //}

                        Text("Quinconces"/*destination.last()*/, fontSize = 18.sp, modifier = Modifier
                            .offset(y = /*if (destination.first() == "") 0.dp else */(-2).dp)
                        )
                    }
                }
            }
        }*/
    }
}