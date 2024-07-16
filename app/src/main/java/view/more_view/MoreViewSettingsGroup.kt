package view.more_view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.preferences_data_store.StoreChosenNetwork

@Composable
fun MoreViewSettingsGroup(navController: NavController) {
    val colorScheme = !isSystemInDarkTheme()
    val context = LocalContext.current
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
    }

    Text(
        text = "Réglages",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = if (colorScheme) Color.Black else Color.White,
        modifier = Modifier
            .padding(start = 15.dp)
            .padding(bottom = 3.dp)
    )

    MoreViewSettingsChosenNetworkRow(navController)

    if(network.value == "tbm") {
        Spacer(modifier = Modifier
            .height(30.dp)
        )

        MoreViewSettingsDisplayNotifCountRow()
    }
}