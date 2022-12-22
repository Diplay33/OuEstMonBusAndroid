package view.lines_map_list.line_map

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch
import model.DTO.Service

@Composable
fun LineMapViewServicesListGroup(
    services: List<Service>,
    selectedService: MutableState<Service?>,
    cameraPositionState: CameraPositionState
) {
    val isCollapsed = remember {
        mutableStateOf(false)
    }
    val currentRotation = remember {
        mutableStateOf(0f)
    }
    val rotation = remember {
        Animatable(currentRotation.value)
    }
    val scope = rememberCoroutineScope()
    val colorScheme = !isSystemInDarkTheme()

    Column(modifier = Modifier
        .padding(bottom = 15.dp)
    ) {
        if(services.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = services.first().vehicle.model,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
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
                            scope.launch {
                                rotation.animateTo(
                                    targetValue = if (isCollapsed.value) -90f else 0f,
                                    animationSpec = tween(200, easing = LinearEasing)
                                )
                            }
                        }
                )
            }
        }

        if(!isCollapsed.value) {
            services.forEach { service ->
                LineMapViewServicesListRow(service, selectedService, cameraPositionState)
            }
        }
    }
}