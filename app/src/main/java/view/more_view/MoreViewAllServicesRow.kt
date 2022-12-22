package view.more_view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import view.Screens.PlusScreens

@Composable
fun MoreViewAllServicesRow(navController: NavController) {
    val colorScheme = !isSystemInDarkTheme()

    Column {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .height(45.dp)
            .fillMaxWidth()
            .background(
                if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                navController.navigate(PlusScreens.AllServicesList.route)
            }
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 15.dp)
                .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Liste des véhicules",
                    fontSize = 18.sp,
                    color = if (colorScheme) Color.Black else Color.White
                )

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        tint = if (colorScheme) Color.Black else Color.White
                    )
                }
            }
        }

        Text(text = "Affiche la liste de tous les véhicules actuellement en circulation sur le réseau TBM",
            color = Color.Gray,
            modifier = Modifier
                .padding(horizontal = 30.dp)
        )
    }
}