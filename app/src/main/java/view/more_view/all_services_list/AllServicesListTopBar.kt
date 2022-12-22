package view.more_view.all_services_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
    services: SnapshotStateList<List<Service>>,
    isLoading: MutableState<Boolean>,
    refreshDate: MutableState<Date>
) {
    val colorScheme = !isSystemInDarkTheme()

    TopAppBar(
        backgroundColor = if (colorScheme) Color.White else Color.Black,
        elevation = 0.dp
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Liste des véhicules",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = if (colorScheme) Color.Black else Color.White,
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
                        tint = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .clickable {
                                isLoading.value = true
                                Services.getAllServices {
                                    services.clear()
                                    services.addAll(Services.filterServicesByVehicle(it))
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