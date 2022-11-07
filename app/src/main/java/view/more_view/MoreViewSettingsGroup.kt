package view.more_view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoreViewSettingsGroup() {
    Text("Réglages", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier
        .padding(start = 15.dp)
    )

    MoreViewSettingsDisplayNotifCountRow()
}