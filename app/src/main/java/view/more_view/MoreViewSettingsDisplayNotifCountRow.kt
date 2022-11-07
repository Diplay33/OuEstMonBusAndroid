package view.more_view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoreViewSettingsDisplayNotifCountRow() {
    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            Color(0xffF5F5F5),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ) {
            Text(text = "Afficher le nombre de messages", fontSize = 18.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
            )

            Switch(checked = true, onCheckedChange = { })
        }
    }
}