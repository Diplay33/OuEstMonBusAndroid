package view.more_view.all_services_list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.Service
import model.DTO.Services

@Composable
fun AllServicesListMain(navController: NavController) {
    Scaffold(topBar = { AllServicesListTopBar(navController) }) {
        val services = remember {
            mutableStateListOf<MutableList<Service>>()
        }
        Services.getServicesByVehicle { servicesByVehicle ->
            services.addAll(servicesByVehicle)
        }

        LazyColumn {
            items(services) { services ->
                AllServicesListGroup(services)
            }

            item {
                Spacer(modifier = Modifier
                    .height(50.dp)
                )
            }
        }
    }
}