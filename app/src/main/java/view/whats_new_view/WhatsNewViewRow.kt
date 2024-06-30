package view.whats_new_view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.WhatsNewElement

@Composable
fun WhatsNewViewRow(whatsNewElement: WhatsNewElement, modifier: Modifier = Modifier) {
    val colorScheme = !isSystemInDarkTheme()

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Icon(
            imageVector = whatsNewElement.icon,
            contentDescription = null,
            tint = Color(android.graphics.Color.parseColor("#007AFF")),
            modifier = Modifier
                .size(50.dp)
        )
        
        Spacer(modifier = Modifier
            .size(7.dp)
        )
        
        Column {
            Text(
                text = whatsNewElement.title,
                fontWeight = FontWeight.Bold,
                color = if (colorScheme) Color.Black else Color.White,
                fontSize = 15.sp
            )

            Text(
                text = whatsNewElement.bodyText,
                color = Color.Gray,
                fontSize = 15.sp
            )
        }
    }
}