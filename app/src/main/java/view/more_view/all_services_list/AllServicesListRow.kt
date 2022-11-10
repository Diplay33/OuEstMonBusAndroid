package view.more_view.all_services_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.*

@Composable
fun AllServicesListRow(service: Service) {
    val line = Lines.getLine(service.lineId.toString())
    val destination = Destinations.getDestinationFromRaw(service.destination)

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(bottom = 8.dp)
        .fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .background(
                color = colorResource(line.lineColorResource).copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 15.dp)
            .padding(top = 7.dp, bottom = if (destination.first() == "") 7.dp else 5.dp)
            .fillMaxWidth()
        ) {
            Column {
                Row {
                    Image(
                        painter = painterResource(id = line.lineImageResource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier
                        .width(10.dp)
                    )

                    Text(
                        text = line.lineName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                Row {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .offset(x = (-7).dp)
                    )

                    Column {
                        if(destination.first() != "") {
                            Text(
                                text = if (service.destination == "Les Pins" && service.lineId == 9) "MARTIGNAS SUR JALLE" else destination.first(),
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .offset(y = if (destination.first() == "") 0.dp else 2.dp)
                            )
                        }

                        Text(destination.last(), fontSize = 18.sp, modifier = Modifier
                            .offset(y = if (destination.first() == "") 0.dp else (-2).dp)
                        )
                    }
                }
            }

            Row(modifier = Modifier
                .align(Alignment.CenterVertically)
            ) {
                Text(service.vehicle.parkId, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier
                    .width(7.dp)
                )

                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}