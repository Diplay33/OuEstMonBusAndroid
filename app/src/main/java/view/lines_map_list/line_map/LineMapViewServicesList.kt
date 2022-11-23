package view.lines_map_list.line_map

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Service
import model.DTO.Services
import view.lines_map_list.NotificationCountBadge
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun LineMapViewServicesList(
    services: List<Service>,
    programmedMessagesCount: Int,
    refreshDate: Date,
    selectedService: MutableState<Service?>
) {
    val formatter = SimpleDateFormat("HH:mm")

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = "Liste des véhicules",
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically)
        )

        if(programmedMessagesCount > 0) {
            Box(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 15.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.MailOutline,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp)
                )

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier
                    .width(40.dp)
                ) {
                    SmallerNotificationCountBadge(programmedMessagesCount)
                }
            }
        }
    }

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
    ) {
        Services.filterServicesByVehicle(services).forEach { services ->
            LineMapViewServicesListGroup(services, selectedService)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
        ) {
            Text(
                text = when(services.size) {
                    0 -> "Aucun véhicule ne circule sur la ligne"
                    1 -> "1 véhicule circule sur la ligne"
                    else -> "${services.size} véhicules circulent sur la ligne"
                },
                color = Color.Gray,
                fontSize = 18.sp
            )

            Text(
                text = "Dernière actualisation à ${formatter.format(refreshDate)}",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
    }
}