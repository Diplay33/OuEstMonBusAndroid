package view.whats_new_view.network_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NetworkPickerMain(navController: NavController) {
    Scaffold(topBar = { NetworkPickerTopBar(navController = navController) }) { padding ->
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxHeight()
            .padding(padding)
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
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier
                    .size(50.dp)
                )
            }
        }

    }
}