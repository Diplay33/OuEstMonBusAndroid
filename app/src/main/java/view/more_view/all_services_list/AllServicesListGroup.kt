package view.more_view.all_services_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Service

@Composable
fun AllServicesListGroup(services: List<Service>) {
    Column(modifier = Modifier
        .padding(bottom = 15.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ) {
            if(services.isNotEmpty()) {
                Text(services.first().vehicle.model, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
        services.forEach { service ->
            AllServicesListRow(service)
        }
    }
}