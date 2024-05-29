package view.next_schedules.search_line.search_stop_list.next_line_schedules.line_schedules

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import model.DTO.Line
import model.DTO.Path
import model.DTO.Schedule
import java.util.*

@Composable
fun LineSchedulesGroup(
    schedules: List<Schedule>,
    line: Line?,
    paths: List<Path>,
    collapsedGroupHandler: MutableList<Boolean>,
    groupIndex: Int
) {
    val colorScheme = !isSystemInDarkTheme()
    val firstScheduleCalendar = Calendar.getInstance()
    firstScheduleCalendar.time = schedules.first().getTime() ?: Date()
    val scope = rememberCoroutineScope()
    val isCollapsed = remember {
        mutableStateOf(collapsedGroupHandler[groupIndex])
    }
    val currentRotation = remember {
        mutableStateOf(if (isCollapsed.value) -90f else 0f)
    }
    val rotation = remember {
        Animatable(currentRotation.value)
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            Text(
                text = "${firstScheduleCalendar.get(Calendar.HOUR_OF_DAY)}h",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White
            )

            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .size(35.dp)
                    .rotate(rotation.value)
                    .clickable {
                        isCollapsed.value = !isCollapsed.value
                        collapsedGroupHandler[groupIndex] = isCollapsed.value
                        scope.launch {
                            rotation.animateTo(
                                targetValue = if (isCollapsed.value) -90f else 0f,
                                animationSpec = tween(200, easing = LinearEasing)
                            )
                        }
                    }
            )
        }

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
            if(!isCollapsed.value) {
                schedules.forEach { schedule ->
                    LineSchedulesRow(line, schedule, paths.first { it.id == schedule.pathId })

                    if(schedule != schedules.last()) {
                        Row {
                            Spacer(modifier = Modifier
                                .width(69.dp)
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