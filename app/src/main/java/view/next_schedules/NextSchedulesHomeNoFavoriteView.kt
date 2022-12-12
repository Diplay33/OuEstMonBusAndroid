package view.next_schedules

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NextSchedulesHomeNoFavoriteView() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Rounded.Favorite,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .size(175.dp)
        )
        
        Text(
            text = "Ajouter des arrêts en favoris",
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )

        Text(
            text = "Vous pouvez ajouter des arrêts en favoris, et les visionner ici. L'ajout d'" +
                "un arrêt se fait directement depuis la vue de celui-ci, depuis les recherches, " +
                "via le bouton favori situé en haut à droite de l'écran.",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.LightGray,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
    }
}