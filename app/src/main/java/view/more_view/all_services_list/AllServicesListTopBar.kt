package view.more_view.all_services_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AllServicesListTopBar(navController: NavController) {
    TopAppBar(backgroundColor = Color.White, elevation = 0.dp) {
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                Text("Liste des véhicules",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )

                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {  }
                        .size(30.dp)
                )
            }
        }
    }
}