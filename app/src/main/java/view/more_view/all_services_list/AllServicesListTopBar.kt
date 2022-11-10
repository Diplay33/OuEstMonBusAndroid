package view.more_view.all_services_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.DTO.Service
import model.DTO.Services
import java.util.*

@Composable
fun AllServicesListTopBar(
    navController: NavController,
    services: SnapshotStateList<Service>,
    isLoading: MutableState<Boolean>,
    refreshDate: MutableState<Date>
) {
    TopAppBar(backgroundColor = Color.White, elevation = 0.dp) {
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                Text("Liste des véhicules",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )

                if(isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically)
                    )
                }
                else {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                isLoading.value = true
                                Services.getAllServices {
                                    services.clear()
                                    services.addAll(Services.filterServicesSortedByVehicle(it))
                                    isLoading.value = false
                                    refreshDate.value = Calendar.getInstance().time
                                }
                            }
                            .size(30.dp)
                    )
                }
            }
        }
    }
}