package view.next_schedules.search_line

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.Path
import view.Screens.ProchainsScreens

@Composable
fun SearchLineViewDestinationRow(
    paths: List<Path>,
    destinations: List<List<String>> = listOf(),
    lineId: String,
    navController: NavController
) {
    val destinationsSet = remember {
        mutableSetOf<String>()
    }

    LaunchedEffect(paths) {
        paths.forEach { path ->
            destinationsSet.add(path.getDestinationName())
        }
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .background(
            Color.White.copy(alpha = 0.4f),
            shape = RoundedCornerShape(10.dp)
        )
        .clickable {
            navController.navigate(ProchainsScreens.SearchStopList.withArgs(
                lineId, paths.first().direction
            ))
        }
        .padding(horizontal = 15.dp)
        .padding(vertical = 2.dp)
        .fillMaxWidth()
    ) {
        Row {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .offset(x = (-7).dp)
            )

            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
            ) {
                if(destinations.isEmpty()) {
                    destinationsSet.forEach { destination ->
                        Text(text = destination, fontSize = 18.sp, modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(fraction = 0.85f)
                        )

                        if(destination != destinationsSet.last()) {
                            Box(modifier = Modifier
                                .clip(RectangleShape)
                                .background(Color.LightGray)
                                .fillMaxWidth(fraction = 0.85f)
                                .height(1.dp)
                            )
                        }
                    }
                }
                else {
                    destinations.forEach { destination ->
                        Column {
                            Text(
                                text = destination.first(),
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .offset(y = 2.dp)
                            )

                            Text(destination.last(), fontSize = 18.sp, modifier = Modifier
                                .offset(y = (-2).dp)
                            )
                        }

                        if(destination != destinations.last()) {
                            Box(modifier = Modifier
                                .clip(RectangleShape)
                                .background(Color.LightGray)
                                .fillMaxWidth(fraction = 0.85f)
                                .height(1.dp)
                            )
                        }
                    }
                }
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null, modifier = Modifier
            .align(Alignment.CenterVertically)
        )
    }
}