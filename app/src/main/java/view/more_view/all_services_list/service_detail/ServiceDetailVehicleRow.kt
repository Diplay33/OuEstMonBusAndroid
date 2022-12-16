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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.R

@Composable
fun ServiceDetailVehicleRow(vehicleModel: String, lineName: String) {
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
            //Need to precise Tram letter, and not using .contains() because Navette Tram exists
            Image(
                painter = painterResource(id = when(lineName) {
                    "Tram A" -> R.drawable.tram
                    "Tram B" -> R.drawable.tram
                    "Tram C" -> R.drawable.tram
                    "Tram D" -> R.drawable.tram
                    "BatCUB" -> R.drawable.ferry
                    else -> R.drawable.bus
                }),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            Text(vehicleModel, fontSize = 18.sp)
        }
    }
}