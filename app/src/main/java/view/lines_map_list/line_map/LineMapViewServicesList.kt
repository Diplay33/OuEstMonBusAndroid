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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Service
import model.DTO.Services
import view.lines_map_list.NotificationCountBadge

@Composable
fun LineMapViewServicesList(services: List<Service>, programmedMessagesCount: Int) {
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
            LineMapViewServicesListGroup(services)
        }
    }
}