package view.whats_new_view.network_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NetworkPickerTopBar(navController: NavController) {
    val colorScheme = !isSystemInDarkTheme()

    TopAppBar(
        backgroundColor = if (colorScheme) Color.White else Color.Black,
        elevation = 0.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .clickable { navController.navigateUp() }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = Color(android.graphics.Color.parseColor("#007AFF")),
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = "Retour",
                fontSize = 18.sp,
                color = Color(android.graphics.Color.parseColor("#007AFF"))
            )
        }
    }
}