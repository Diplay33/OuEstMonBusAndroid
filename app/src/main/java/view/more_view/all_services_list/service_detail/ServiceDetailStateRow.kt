package view.more_view.all_services_list.service_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ouestmonbus.R
import kotlin.math.abs

@Composable
fun ServiceDetailStateRow(state: String, stateTime: Int) {
    val stateMinutes = (abs(stateTime) % 3600) / 60
    val stateSeconds = (abs(stateTime) % 3600) % 60

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            Color(0xffF5F5F5),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.timer),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            when(state) {
                "HEURE" -> {
                    Text(
                        text = "À l'heure",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff6CC76E)
                    )
                }
                "AVANCE" -> {
                    Text(
                        text = "En avance de ${stateMinutes}min$stateSeconds",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (abs(stateTime) < 181) Color(0xffF19138) else Color.Red
                    )
                }
                "RETARD" -> {
                    Text(
                        text = "En retard de ${stateMinutes}min$stateSeconds",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (stateTime < 301)
                            Color(0xffF19138)
                        else
                            Color.Red
                    )
                }
            }
        }
    }
}