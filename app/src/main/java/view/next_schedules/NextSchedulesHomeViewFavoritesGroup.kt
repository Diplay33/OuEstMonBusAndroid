package view.next_schedules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import model.DTO.Lines
import model.preferences_data_store.StoreFavoriteStopsWithLine

@Composable
fun NextSchedulesHomeViewFavoritesGroup() {
    val favoriteStopsWithLine = remember {
        mutableMapOf<String, List<String>>()
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val storeFavStopsWithLine = StoreFavoriteStopsWithLine(context)

    LaunchedEffect("r") {
        Lines.getAllLines().forEach { line ->
            scope.launch {
                storeFavStopsWithLine.getFavoriteStopsForLine(line.id.toString()) { set ->
                    favoriteStopsWithLine[line.id.toString()] = set.toList()
                }
            }
        }
    }

    Column {
        Text(
            text = "Arrêts favoris",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 15.dp)
                .clickable {
                    println(favoriteStopsWithLine.size)
                    println(favoriteStopsWithLine)
                }
        )

        Column {
            Lines.getAllLines().forEach { line ->
                NextSchedulesHomeFavoriteRow("${line.lineName} : ${favoriteStopsWithLine[line.id.toString()]}")
            }
        }
    }
}