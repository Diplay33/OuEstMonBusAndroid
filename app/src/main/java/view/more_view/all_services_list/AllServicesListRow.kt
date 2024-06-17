package view.more_view.all_services_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.diplay.ouestmonbus.R
import model.DTO.*
import view.Screens.PlusScreens

@Composable
fun AllServicesListRow(service: Service, navController: NavController) {
    val line = remember {
        mutableStateOf<Line?>(null)
    }
    val destination = remember {
        mutableStateOf<Destination?>(null)
    }
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(service) {
        Lines.getLine(service.lineId) { line.value = it }
        Destinations.getDestination(service.destination, service.lineId) { destination.value = it }
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
            .padding(top = 7.dp, bottom = if (destination.value == null) 7.dp else 5.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    PlusScreens.ServiceDetail.withArgs(
                        line.value?.id.toString(),
                        service.vehicleId.toString(),
                        service.destination,
                        service.latitude.toString(),
                        service.longitude.toString(),
                        service.currentStop.toString(),
                        service.currentSpeed.toString(),
                        service.state,
                        service.stateTime.toString(),
                        service.path.toString()
                    )
                )
            }
        ) {
            Column {
                Row {
                    if(line.value?.name == "Ligne inconnue") {
                        Image(
                            painter = painterResource(id = R.drawable.question_mark_box),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(line.value?.imageUrl)
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                    Spacer(modifier = Modifier
                        .width(10.dp)
                    )

                    Text(
                        text = line.value?.name ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                Row {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        tint = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .offset(x = (-7).dp)
                    )

                    Column {
                        destination.value?.let { destination ->
                            Text(
                                text = destination.city,
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .offset(y = 2.dp)
                            )

                            Spacer(modifier = Modifier.height(3.dp))
                        }

                        Text(
                            text = destination.value?.destination ?: service.destination,
                            fontSize = 18.sp,
                            color = if (colorScheme) Color.Black else Color.White,
                            modifier = Modifier
                                .offset(y = if (destination.value == null) 0.dp else (-2).dp)
                        )
                    }
                }
            }

            Row(modifier = Modifier
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = service.vehicle.parkId,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier
                    .width(7.dp)
                )

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
}