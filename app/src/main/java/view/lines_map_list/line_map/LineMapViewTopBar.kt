package view.lines_map_list.line_map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Line

@Composable
fun LineMapViewTopBar(navController: NavController, line: Line) {
    TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
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

            Text(
                text = line.lineName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(
                        color = colorResource(id = line.lineColorResource),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(7.dp)
            )

            Icon(
                imageVector = Icons.Rounded.LocationOn, //TODO: Find the right icon
                contentDescription = null,
                modifier = Modifier
                    .clickable { }
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}