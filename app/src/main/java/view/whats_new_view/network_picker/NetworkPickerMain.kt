package view.whats_new_view.network_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.DTO.Networks
import model.SupabaseManager
import model.preferences_data_store.StoreChosenNetwork

@Composable
fun NetworkPickerMain(
    navController: NavController,
    refreshLinesAction: MutableState<String?>,
    showSplashScreen: MutableState<Boolean>
) {
    val colorScheme = !isSystemInDarkTheme()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val chosenNetworkDataStore = StoreChosenNetwork(context)
    val selection = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        chosenNetworkDataStore.chosenNetwork.collect { selection.value = it ?: "" }
    }

    Scaffold(topBar = { NetworkPickerTopBar(navController = navController) }) { padding ->
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxHeight()
            .padding(padding)
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(0.1f)
            ) {
                Spacer(modifier = Modifier
                    .size(50.dp)
                )

                Text(
                    text = "Choisissez votre réseau de transports",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )

                Spacer(modifier = Modifier
                    .size(50.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Networks.getAllNetworks().forEach { network ->
                        NetworkPickerRow(selection, network)
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 20.dp)
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color.Black.copy(0.7f),
                        ambientColor = Color.Black.copy(0.3f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selection.value == "")
                            if (colorScheme)
                                Color.LightGray
                            else
                                Color.DarkGray
                        else
                            Color(android.graphics.Color.parseColor("#007AFF"))
                    )
                    .clickable(enabled = selection.value != "") {
                        scope.launch {
                            chosenNetworkDataStore.setNetwork(selection.value)
                            withContext(Dispatchers.IO) {
                                SupabaseManager.beginSyncDatabaseProcess(selection.value, context) {
                                    refreshLinesAction.value = it
                                }
                            }
                            if (!showSplashScreen.value) {
                                navController.navigateUp()
                            } else {
                                showSplashScreen.value = false
                            }
                        }
                    }
            ) {
                Text(
                    text = "Confirmer",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}