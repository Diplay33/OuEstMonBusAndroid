package view.more_view.all_services_list.service_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.R

@Composable
fun ServiceDetailOperatorRow(operator: String) {
    val colorScheme = !isSystemInDarkTheme()
    val id = if (operator.contains("Keolis"))
        R.drawable.keolis
    else
        if (operator.contains("TRANSDEV"))
            R.drawable.transdev
        else
            if (operator.contains("River Cruise"))
                R.drawable.river_cruise
            else
                if (operator.contains("RGO Mobilités"))
                    R.drawable.rgo_mobilites
                else
                    if (operator.contains("Linevia"))
                        R.drawable.linevia
                    else
                        if (operator.contains("Evadys"))
                            R.drawable.evadys
                        else
                            0

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .align(Alignment.CenterVertically)
        ) {
            if(id != 0) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = null, modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier
                .width((if (id == 0) 35 else 15).dp)
            )

            Text(
                text = operator,
                fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White
            )
        }
    }
}