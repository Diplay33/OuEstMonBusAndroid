package view.next_schedules

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DTO.*
import model.preferences_data_store.StoreFavoriteStopsWithLine
import view.Screens.ProchainsScreens
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesView

@Composable
fun NextSchedulesHomeFavoriteRow(
    navController: NavController,
    station: Station,
    lines: List<LineR>,
    favoriteStopsSet: MutableList<Station>
) {
    val nextSchedules = remember {
        mutableStateListOf<NextSchedule>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val storeFavStopsWithLine = StoreFavoriteStopsWithLine(context)
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(station) {
        while(true) {
            NextSchedules.getNextSchedulesByStationId(station.stationId) { values ->
                nextSchedules.clear()
                nextSchedules.addAll(values)
                isLoading.value = false
            }

            delay(10000)
        }
    }

    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
        .padding(bottom = 20.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Column(modifier = Modifier
            .padding(vertical = 10.dp)
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
            ) {
                Text(
                    text = station.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            Column {
                lines.forEach { line ->
                    val menuShown = remember {
                        mutableStateOf(false)
                    }

                    Column(modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .background(
                            if (colorScheme) Color.White else Color(0xff18191A),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(
                            Color(android.graphics.Color.parseColor(line.colorHex))
                                .copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                        ) {
                            Row {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(line.imageUrl)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                )

                                Spacer(modifier = Modifier
                                    .width(15.dp)
                                )

                                Text(
                                    text = line.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (colorScheme) Color.Black else Color.White,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                            }

                            Box(contentAlignment = Alignment.Center, modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .clickable { menuShown.value = !menuShown.value }
                            ) {
                                Box(modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray.copy(alpha = 0.2f))
                                    .align(Alignment.Center)
                                )

                                Icon(
                                    imageVector = Icons.Rounded.MoreVert,
                                    contentDescription = null,
                                    tint = if (colorScheme) Color.Black else Color.White
                                )
                            }
                        }

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(android.graphics.Color.parseColor(line.colorHex))
                                    .copy(alpha = 0.2f),
                                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                            )
                        ) {
                            Column {
                                Spacer(modifier = Modifier
                                    .height(15.dp)
                                )

                                NextSchedulesHomeFavoriteView(line, nextSchedules, isLoading.value)

                                Spacer(modifier = Modifier
                                    .height(15.dp)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = menuShown.value,
                            onDismissRequest = { menuShown.value = false },
                            modifier = Modifier
                                .background(if (colorScheme) Color.White else Color(0xff18191A))
                        ) {
                            DropdownMenuItem(onClick = {
                                navController.navigate(ProchainsScreens.FavoriteStopDetail.withArgs(
                                    station.stationId,
                                    station.name,
                                    line.id.toString(),

                                    ))
                            }) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Rounded.Info,
                                        contentDescription = null,
                                        tint = if (colorScheme) Color.Black else Color.White,
                                        modifier = Modifier
                                            .size(30.dp)
                                    )

                                    Spacer(modifier = Modifier
                                        .width(5.dp)
                                    )

                                    Text(
                                        text = "Afficher plus de détails",
                                        color = if (colorScheme) Color.Black else Color.White,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }

                            DropdownMenuItem(onClick = {
                                scope.launch {
                                    storeFavStopsWithLine.removeFavoriteStopForLine(
                                        lineId = line.id.toString(),
                                        stopId = station.stationId
                                    )
                                }
                                menuShown.value = false
                                if(lines.size <= 1) {
                                    val temporarySet = favoriteStopsSet.filter { it.stationId != station.stationId }
                                    favoriteStopsSet.clear()
                                    favoriteStopsSet.addAll(temporarySet)
                                }
                            }) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Rounded.Favorite,
                                        contentDescription = null,
                                        tint = if (colorScheme) Color.Black else Color.White,
                                        modifier = Modifier
                                            .size(30.dp)
                                    )

                                    Spacer(modifier = Modifier
                                        .width(5.dp)
                                    )

                                    Text(
                                        text = "Retirer des favoris",
                                        color = if (colorScheme) Color.Black else Color.White,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}