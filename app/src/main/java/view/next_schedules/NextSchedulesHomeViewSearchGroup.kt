package view.next_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import view.Screens.ProchainsScreens

@Composable
fun NextSchedulesHomeViewSearchGroup(navController: NavController) {
    Column {
        Text(
            text = "Rechercher par",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 15.dp)
        )

        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .height(45.dp)
            .fillMaxWidth()
            .background(
                Color(0xffF5F5F5),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { navController.navigate(ProchainsScreens.SearchLineView.route) }
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .align(Alignment.CenterVertically)
            ) {
                Text(text = "Ligne", fontSize = 18.sp)

                Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null)
            }
        }
    }
}