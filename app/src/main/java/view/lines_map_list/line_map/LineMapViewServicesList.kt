package view.lines_map_list.line_map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Service
import model.DTO.Services

@Composable
fun LineMapViewServicesList(services: List<Service>) {
    Text(
        text = "Liste des véhicules",
        fontSize = 25.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 15.dp)
    )

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
    ) {
        Services.filterServicesByVehicle(services).forEach { services ->
            LineMapViewServicesListGroup(services)
        }
    }
}