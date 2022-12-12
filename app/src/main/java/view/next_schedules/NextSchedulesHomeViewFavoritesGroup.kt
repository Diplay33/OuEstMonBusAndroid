package view.next_schedules

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import model.DTO.Lines
import model.DTO.Station
import model.DTO.Stations
import model.preferences_data_store.StoreFavoriteStopsWithLine

@Composable
fun NextSchedulesHomeViewFavoritesGroup() {
    val favoriteStopsWithLine = remember {
        mutableStateMapOf<String, List<String>>()
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val storeFavStopsWithLine = StoreFavoriteStopsWithLine(context)
    val favoriteStopsSet = remember {
        mutableStateListOf<Station>()
    }
    val isLoading = remember {
        mutableStateOf(false)
    }
    val refreshValue = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(refreshValue.value) {
        favoriteStopsSet.clear()

        Lines.getAllLines().forEach { line ->
            scope.launch {
                storeFavStopsWithLine.getFavoriteStopsForLine(line.id.toString()) { set ->
                    favoriteStopsWithLine[line.id.toString()] = set.toList()
                    set.forEach { stationId ->
                        isLoading.value = true
                        Stations.getStationByStationId(stationId) { station ->
                            if(favoriteStopsSet.firstOrNull { station.stationId == it.stationId } == null) {
                                favoriteStopsSet.add(station)
                            }
                            isLoading.value = false
                        }
                    }
                }
            }
        }
    }

    if(favoriteStopsSet.isEmpty() && !isLoading.value) {
        NextSchedulesHomeNoFavoriteView()
    }
    else {
        Column {
            Text(
                text = "Arrêts favoris",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 15.dp)
            )

            if(isLoading.value) {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            else {
                Column {
                    favoriteStopsSet.sortedBy { it.name }.forEach { station ->
                        NextSchedulesHomeFavoriteRow(
                            station = station,
                            lines = Lines.getAllLines().filter { line ->
                                favoriteStopsWithLine[line.id.toString()]?.contains(station.stationId) ?: false
                            },
                            refreshValue = refreshValue
                        )
                    }
                }
            }
        }
    }
}