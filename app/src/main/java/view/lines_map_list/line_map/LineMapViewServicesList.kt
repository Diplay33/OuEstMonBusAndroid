package view.lines_map_list.line_map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
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
import com.diplay.ouestmonbus.BuildConfig
import com.google.maps.android.compose.CameraPositionState
import model.DTO.Service
import model.DTO.Services
import view.lines_map_list.NotificationCountBadge
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.abs

@Composable
fun LineMapViewServicesList(
    services: List<Service>,
    programmedMessagesCount: Int,
    refreshDate: Date,
    selectedService: MutableState<Service?>,
    areMessagesDisplayed: MutableState<Boolean>,
    isLoading: Boolean,
    cameraPositionState: CameraPositionState
) {
    val formatter = SimpleDateFormat("HH:mm")
    val colorScheme = !isSystemInDarkTheme()
    val lateAverage = if (services.isEmpty()) 0 else services.map { it.stateTime }.reduce { x, y -> x + y } / services.count()

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = "Liste des véhicules",
            fontSize = 25.sp,
            color = if (colorScheme) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically)
        )

        if(programmedMessagesCount > 0) {
            Box(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 15.dp)
                .clickable {
                    areMessagesDisplayed.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.MailOutline,
                    contentDescription = null,
                    tint = if (colorScheme) Color.Black else Color.White,
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


    if(isLoading) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
    else {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
        ) {
            Services.filterServicesByVehicle(services).forEach { services ->
                LineMapViewServicesListGroup(services, selectedService, cameraPositionState)
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
                    text = "Retard moyen sur la ligne : ${lateAverageFormatter(lateAverage)}",
                    color = Color.Gray,
                    fontSize = 18.sp
                )

                Text(
                    text = "Dernière actualisation à ${formatter.format(refreshDate)}",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier
                .height((if (BuildConfig.DEBUG) 15 else 60).dp)
            )
        }
    }
}

fun lateAverageFormatter(lateAverage: Int): String {
    val computedValue = if (lateAverage < 0) 0 else lateAverage
    var returnString = ""
    if(computedValue > 59) {
        returnString = "${(computedValue % 3600) / 60}min"
    }
    val computedSecValue = (computedValue % 3600) % 60
    returnString += "$computedSecValue" + if (returnString.isEmpty()) if(computedSecValue < 2) " seconde" else " secondes" else ""
    return returnString
}