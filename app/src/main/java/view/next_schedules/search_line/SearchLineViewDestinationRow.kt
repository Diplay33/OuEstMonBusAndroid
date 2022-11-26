package view.next_schedules.search_line

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchLineViewDestinationRow() {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .background(
            Color.White.copy(alpha = 0.4f),
            shape = RoundedCornerShape(10.dp)
        )
        .clickable { }
        .padding(horizontal = 15.dp)
        .padding(vertical = 2.dp)
        .fillMaxWidth()
    ) {
        Row {
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

        Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null, modifier = Modifier
            .align(Alignment.CenterVertically)
        )
    }
}