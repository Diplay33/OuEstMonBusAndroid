package view.more_view.all_services_list.service_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import model.DTO.Line

@Composable
fun ServiceDetailHeader(line: Line, destination: List<String>) {
    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .fillMaxWidth()
        .background(
            color = colorResource(line.lineColorResource).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Image(
            painter = painterResource(id = line.lineImageResource),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 15.dp)
                .width(70.dp)
                .height(55.dp)
                .align(Alignment.CenterVertically)
        )

        Column(modifier = Modifier
            .padding()
            .offset(y = 3.dp)
        ) {
            Text(
                text = line.lineName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )

            Row(modifier = Modifier
                .offset(y = (-5).dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .offset(x = 4.dp)
                )

                Column(modifier = Modifier
                    .padding(horizontal = 10.dp)
                ) {
                    if(destination.first() != "") {
                        Text(
                            text = if (destination.last() == "Les Pins" && line.id == 9)
                                "MARTIGNAS SUR JALLE"
                            else
                                destination.first(),
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
    }
}