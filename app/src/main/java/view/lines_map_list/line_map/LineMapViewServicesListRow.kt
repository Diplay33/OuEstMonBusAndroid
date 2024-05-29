package view.lines_map_list.line_map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import model.DTO.Destinations
import model.DTO.LineR
import model.DTO.LinesR
import model.DTO.Service

@Composable
fun LineMapViewServicesListRow(
    service: Service,
    selectedService: MutableState<Service?>,
    cameraPositionState: CameraPositionState,
) {
    val line = remember {
        mutableStateOf<LineR?>(null)
    }
    val destination = Destinations.getDestinationFromRaw(service.destination, service.lineId)
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(service) {
        LinesR.getLine(service.lineId) { line.value = it }
    }

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(bottom = 8.dp)
        .fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .background(
                if (colorScheme) Color.Transparent else Color(0xff18191A),
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = if (line.value == null)
                    Color.Transparent
                else
                    Color(android.graphics.Color.parseColor(line.value?.colorHex)).copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 15.dp)
            .padding(top = 7.dp, bottom = if (destination.first() == "") 7.dp else 5.dp)
            .fillMaxWidth()
            .clickable {
                selectedService.value = service
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(service.latitude - 0.013, service.longitude), 13f
                )
            }
        ) {
            Row {
                Text(
                    text = service.vehicle.parkId,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .width(if (service.vehicle.parkId.count() <= 4) 46.dp else 68.dp)
                        .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier
                    .width(15.dp)
                )

                Column {
                    if(destination.first() != "") {
                        Text(
                            text = destination.first(),
                            fontSize = 13.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .offset(y = if (destination.first() == "") 0.dp else 2.dp)
                        )

                        Spacer(modifier = Modifier.height(3.dp))
                    }

                    Text(
                        text = destination.last(),
                        fontSize = 18.sp,
                        color = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .offset(y = if (destination.first() == "") 0.dp else (-2).dp)
                    )
                }
            }

            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}