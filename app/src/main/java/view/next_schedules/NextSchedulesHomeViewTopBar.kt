package view.next_schedules

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
fun NextSchedulesHomeViewTopBar() {
    TopAppBar(backgroundColor = Color.White, elevation = 0.dp) {
        Text(
            text = "Prochains passages",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
    }
}