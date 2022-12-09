package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import model.DTO.Line
import model.preferences_data_store.StoreFavoriteStopsWithLine

@Composable
fun NextLineSchedulesTopBar(
    navController: NavController,
    stopId: String,
    stopName: String,
    line: Line
) {
    TopAppBar(backgroundColor = Color.White, elevation = 0.dp) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val storeFavStopsWithLine = StoreFavoriteStopsWithLine(context)
        val isFavorite = remember {
            mutableStateOf(false)
        }
        val isMaxFavoritesReached = remember {
            mutableStateOf(false)
        }
        val isDialogShown = remember {
            mutableStateOf(false)
        }

        LaunchedEffect(line) {
            scope.launch {
                storeFavStopsWithLine.getFavoriteStopsForLine(line.id.toString()) { favoriteStops ->
                    isFavorite.value = favoriteStops.contains(stopId)
                }
            }
            scope.launch {
                storeFavStopsWithLine.checkMaxStopCountReached {
                    println(it)
                    isMaxFavoritesReached.value = it
                }
            }
        }

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
                Text(
                    text = stopName,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(fraction = 0.9f)
                )

                Icon(
                    imageVector = if (isFavorite.value)
                        Icons.Rounded.Favorite
                    else
                        Icons.Rounded.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            scope.launch {
                                if(isFavorite.value) {
                                    storeFavStopsWithLine.removeFavoriteStopForLine(
                                        lineId = line.id.toString(),
                                        stopId = stopId
                                    )
                                    storeFavStopsWithLine.checkMaxStopCountReached { result ->
                                        isMaxFavoritesReached.value = result
                                    }
                                    isFavorite.value = false
                                } else {
                                    if (isMaxFavoritesReached.value) {
                                        isDialogShown.value = true
                                    } else {
                                        storeFavStopsWithLine.saveFavoriteStopForLine(
                                            lineId = line.id.toString(),
                                            stopId = stopId
                                        )
                                        storeFavStopsWithLine.checkMaxStopCountReached { result ->
                                            isMaxFavoritesReached.value = result
                                        }
                                        isFavorite.value = true
                                    }
                                }
                            }
                        }
                )
            }
        }

        if(isDialogShown.value) {
            AlertDialog(
                onDismissRequest = {
                    isDialogShown.value = false
                },
                title = {
                    Text("Nombre maximum d'arrêts en favori atteint")
                },
                text = {
                    Text("Le nombre maximum d'arrêts en favori (10) a été atteint, veuillez " +
                            "en supprimer pour pouvoir ajouter celui-ci. Si d'autres lignes " +
                            "passent par cet arrêt, vous pourrez cependant ajouter ces autres " +
                            "lignes en favori avec cet arrêt.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            isDialogShown.value = false
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}