package view.whats_new_view.network_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NetworkPickerMain() {
    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxHeight()
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(0.1f)
        ) {
            Text(text = "Choisissez votre réseau de transports", modifier = Modifier
                .fillMaxWidth()
            )
        }
    }
}