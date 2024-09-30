package view.more_view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.preferences_data_store.StoreChosenNetwork

@Composable
fun MoreViewMain(navController: NavController) {
    val colorScheme = !isSystemInDarkTheme()
    val context = LocalContext.current
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { network.value = it ?: "" }
    }

    Scaffold(topBar = { MoreTopBar() } ) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .fillMaxHeight()
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            item {
                MoreViewContactGroup()

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                if(network.value == "tbm") {
                    MoreViewWebVersionRow()

                    Spacer(modifier = Modifier
                        .height(30.dp)
                    )
                }

                MoreViewAllServicesRow(navController)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                MoreViewSettingsGroup(navController)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                MoreViewAppInfoGroup()
            }
        }
    }
}