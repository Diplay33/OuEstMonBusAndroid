package view.more_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Network
import model.DTO.Networks
import model.preferences_data_store.StoreChosenNetwork
import view.Screens.SplashScreens
import view.whats_new_view.network_picker.computeNetworkImage

@Composable
fun MoreViewSettingsChosenNetworkRow(navController: NavController) {
    val colorScheme = !isSystemInDarkTheme()
    val context = LocalContext.current
    val chosenNetworkDataStore = StoreChosenNetwork(context)
    val chosenNetwork = remember {
        mutableStateOf<Network?>(null)
    }

    LaunchedEffect(Unit) {
        chosenNetworkDataStore.chosenNetwork.collect { value ->
            value?.let {
                chosenNetwork.value = Networks.getNetwork(it)
            }
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
        .clickable { navController.navigate(SplashScreens.NetworkPicker.route)  }
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ) {
            Text(
                text = "Choix du réseau",
                fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                chosenNetwork.value?.let { network ->
                    Image(
                        painter = painterResource(id = computeNetworkImage(network)),
                        contentDescription = null,
                        modifier = Modifier
                            .height(32.dp)
                            .padding(end = 8.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = if (colorScheme) Color.Black else Color.White
                )
            }
        }
    }
}