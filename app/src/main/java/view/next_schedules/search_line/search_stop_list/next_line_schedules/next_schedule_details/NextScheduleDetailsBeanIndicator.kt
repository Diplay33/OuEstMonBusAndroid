package view.next_schedules.search_line.search_stop_list.next_line_schedules.next_schedule_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diplay.ouestmonbus.R
import kotlinx.coroutines.delay

@Composable
fun NextScheduleDetailsBeanIndicator() {
    val colorScheme = !isSystemInDarkTheme()
    val animationState = remember {
        mutableStateOf(false)
    }
    val indicatorOpacityState = if (animationState.value) 0.1f else 0.8f
    val indicatorOpacity by animateFloatAsState(targetValue = indicatorOpacityState, label = "")

    LaunchedEffect("") {
        delay(1000)
        while(true) {
            animationState.value = !animationState.value
            delay(1000)
        }
    }

    Image(
        painter = painterResource(id = R.drawable.bean_small),
        contentDescription = null,
        colorFilter = ColorFilter.tint(if (colorScheme)
            Color.Black
        else
            Color.White),
        modifier = Modifier
            .size(15.dp)
            .offset(x = 10.dp, y = (-10).dp)
            .alpha(indicatorOpacity)
    )
}