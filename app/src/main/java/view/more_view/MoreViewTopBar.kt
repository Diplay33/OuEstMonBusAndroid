package view.more_view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MoreTopBar() {
    val colorScheme = !isSystemInDarkTheme()

    TopAppBar(
        backgroundColor = if (colorScheme) Color.White else Color.Black,
        elevation = 0.dp
    ) {
        Text("Plus",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            color = if (colorScheme) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
    }
}