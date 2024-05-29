package view.next_schedules.search_line.search_stop_list.next_line_schedules.line_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.DTO.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun LineSchedulesMain(
    navController: NavController,
    stationId: String?,
    stationName: String?,
    lineId: String?,
    direction: String?,
    selectedDate: String?
) {
    val paths = remember {
        mutableStateListOf<Path>()
    }
    val schedules = remember {
        mutableStateListOf<Schedule>()
    }
    val sortedSchedules = Schedules.sortSchedulesByHour(schedules).filter { it.isNotEmpty() }
    val notRealizedSchedules = sortedSchedules.filter { values ->
        values.any { it.state != "REALISE" && it.rawRealTime == "null" }
    }
    val displayMoreSchedules = remember {
        mutableStateOf(false)
    }
    val line = remember {
        mutableStateOf<Line?>(null)
    }
    val collapsedGroupHandler: MutableList<Boolean> = sortedSchedules.map {
        false
    }.toMutableList()
    val loadingCount = remember {
        mutableIntStateOf(0)
    }
    val selectedDateValue = LocalDate.parse(selectedDate)
    val formatter = DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRENCH)
    val colorScheme = !isSystemInDarkTheme()

    LaunchedEffect(stationId) {
        Lines.getLine(lineId?.toInt() ?: 0) { line.value = it }
        Paths.getOrderedAllPathsByLine(lineId?.toInt() ?: 0) { returnedPaths ->
            paths.clear()
            returnedPaths.map { value ->
                if(value.first().direction == direction) {
                    paths.addAll(value)

                    Schedules.getSchedulesByStationAndPathsForDate(
                        stationId = stationId ?: "",
                        paths = value,
                        lineId = (lineId ?: "0").toInt(),
                        date = selectedDateValue ?: LocalDate.now()) { values ->
                        schedules.addAll(values.filter { it.state != "ANNULE" })
                        loadingCount.intValue += 1
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = { LineSchedulesTopBar(navController, stationName ?: "Arrêt inconnu") }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            if(loadingCount.intValue < 2) {
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier
                        .height(10.dp)
                    )

                    Text(
                        text = "Chargement en cours...",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            else {
                if(if (displayMoreSchedules.value)
                        schedules.isEmpty()
                    else
                        notRealizedSchedules.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = if (notRealizedSchedules.isEmpty() && sortedSchedules.isNotEmpty())
                                "Le service est terminé"
                            else
                                "Aucun résultat à l'heure actuelle",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    }
                }
                else {
                    LazyColumn(modifier = Modifier
                        .padding(padding)
                    ) {
                        if(sortedSchedules.count() > notRealizedSchedules.count()
                            && !displayMoreSchedules.value) {
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp)
                                        .background(
                                            if (colorScheme) Color.Transparent else Color(0xff18191A),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .background(
                                            if (line.value == null)
                                                Color.Transparent
                                            else
                                                Color(android.graphics.Color.parseColor(line.value?.colorHex)).copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .height(45.dp)
                                        .clickable {
                                            displayMoreSchedules.value = !displayMoreSchedules.value
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowForward,
                                        contentDescription = null,
                                        tint = if (colorScheme)
                                            if (line.value == null)
                                                Color.Transparent
                                            else
                                                Color(android.graphics.Color.parseColor(line.value?.colorHex))
                                        else
                                            Color.White,
                                        modifier = Modifier
                                            .size(25.dp)
                                            .rotate(-90f)
                                    )

                                    Spacer(modifier = Modifier
                                        .width(10.dp)
                                    )

                                    Text(
                                        text = "Afficher les résultats précédents",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = if (colorScheme)
                                            if (line.value == null)
                                                Color.Transparent
                                            else
                                                Color(android.graphics.Color.parseColor(line.value?.colorHex))
                                        else
                                            Color.White
                                    )
                                }

                                Spacer(modifier = Modifier
                                    .height(30.dp)
                                )
                            }
                        }
                        else {
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp)
                                ) {
                                    Text(
                                        text = formatter
                                            .format(selectedDateValue)
                                            .replaceFirstChar { it.uppercase() },
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = if (colorScheme) Color.Black else Color.White,
                                    )
                                }
                            }
                        }

                        items(if (displayMoreSchedules.value)
                            sortedSchedules.size
                        else
                            notRealizedSchedules.size) { groupIndex ->
                            LineSchedulesGroup(
                                schedules = if (displayMoreSchedules.value)
                                    sortedSchedules[groupIndex]
                                else
                                    notRealizedSchedules[groupIndex],
                                line = line.value,
                                paths = paths,
                                collapsedGroupHandler = collapsedGroupHandler,
                                groupIndex = groupIndex
                            )

                            if(if (displayMoreSchedules.value)
                                    sortedSchedules[groupIndex] != sortedSchedules.last()
                                else
                                    notRealizedSchedules[groupIndex] != notRealizedSchedules.last()) {
                                Spacer(modifier = Modifier
                                    .height(30.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}