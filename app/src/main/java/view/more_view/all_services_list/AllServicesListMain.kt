package view.more_view.all_services_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AllServicesListMain(navController: NavController) {
    Scaffold(topBar = { AllServicesListTopBar(navController) }) {
        LazyColumn {
            item {
                AllServicesListRow()
            }
        }
    }
}