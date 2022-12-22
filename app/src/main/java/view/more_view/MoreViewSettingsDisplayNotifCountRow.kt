package view.more_view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import model.preferences_data_store.StoreDisplayNotifCountParam

@Composable
fun MoreViewSettingsDisplayNotifCountRow() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreDisplayNotifCountParam(context)
    val isEnabled = dataStore.isEnabled.collectAsState(initial = false)
    val colorScheme = !isSystemInDarkTheme()

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ) {
            Text(
                text = "Afficher le nombre de messages",
                fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            Switch(
                checked = if (isEnabled.value == null) false else isEnabled.value!!,
                onCheckedChange = { newValue ->
                    scope.launch {
                        if(newValue) {
                            dataStore.enable()
                        }
                        else {
                            dataStore.disable()
                        }
                    }
                }
            )
        }
    }
}