package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import model.DTO.*

@Composable
fun NextLineSchedulesMain(
    navController: NavController,
    stopName: String?,
    stopId: String?,
    lineId: String?,
    pathDirection: String?
) {
    val line = remember {
        mutableStateOf<Line?>(null)
    }
    val paths = remember {
        mutableStateListOf<Path>()
    }
    val destinations = remember {
        mutableStateListOf<List<String>>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val colorScheme = !isSystemInDarkTheme()
    val mapMarkers = remember {
        mutableStateListOf<NSchedulesMapMarker>()
    }
    val capFilteredNextSchedules = remember {
        mutableStateListOf<NextSchedule>()
    }
    val station = remember {
        mutableStateOf<Station?>(null)
    }
    val stationMarker = remember {
        mutableStateOf(NSchedulesMapMarker(NSchedulesMapMarkerType.STOP, null, null))
    }
    val focusedVehicle = remember {
        mutableStateOf<Int?>(null)
    }
    val pathsCoordinates = remember {
        mutableStateListOf<List<LatLng>>()
    }
    val context = LocalContext.current

    LaunchedEffect(lineId) {
        Lines.getLine(lineId?.toInt() ?: 0) { returnedLine ->
            line.value = returnedLine

            if(pathDirection == "ALLER") {
                AllerDestinations.getListOfDestinations(returnedLine.id) {
                    destinations.addAll(it)
                }
            }
            else {
                RetourDestinations.getListOfDestinations(returnedLine.id) {
                    destinations.addAll(it)
                }
            }
        }
    }

    LaunchedEffect(stopName, line.value) {
        line.value?.let { line ->
            Paths.getOrderedPathsByLine(line.id, true) { returnedPaths ->
                paths.clear()
                returnedPaths.map {
                    if(it.first().direction == pathDirection) {
                        paths.addAll(it)
                    }
                }
                returnedPaths[if ((pathDirection ?: "ALLER") == "ALLER") 0 else 1].forEach { path ->
                    pathsCoordinates.addAll(
                        path.coordinates.map { coordinates ->
                            coordinates.map { LatLng(it[1], it[0]) }
                        }
                    )
                }
            }
        }
        Stations.getStationByStationId(stopId ?: "") {
            station.value = it
            stationMarker.value = NSchedulesMapMarker(NSchedulesMapMarkerType.STOP, it, null)
            mapMarkers.add(stationMarker.value)
        }
        line.value?.let { line ->
            while(true) {
                NextSchedules.getNextSchedulesByStationId(stopId ?: "") { returnedNextSchedules ->
                    capFilteredNextSchedules.clear()
                    returnedNextSchedules.forEach { ns ->
                        if(capFilteredNextSchedules.size < 5 && ns.lineId == line.id) {
                            capFilteredNextSchedules.add(ns)
                        }
                    }
                    isLoading.value = false

                    NSchedulesMapMarkers.retrieveVehicles(context, line.id, capFilteredNextSchedules.map { it.vehicleId ?: 0 }) { vehicles ->
                        mapMarkers.clear()
                        mapMarkers.addAll(vehicles + listOf(stationMarker.value))
                    }
                }
                delay(10000)
            }
        }
    }
    
    Scaffold(topBar = { NextLineSchedulesTopBar(
        navController = navController,
        stopId = stopId.toString(),
        stopName = stopName ?: "Arrêt inconnu",
        line = line.value
    ) }) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .fillMaxHeight()
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            item {
                NextLineSchedulesHeader(line.value, paths, destinations)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextLineSchedulesView(capFilteredNextSchedules, line.value, isLoading.value, focusedVehicle)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextLineSchedulesMap(station.value, line.value, mapMarkers, focusedVehicle, navController, pathsCoordinates)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextLineSchedulesSchdlGroup(navController, line.value, stopId, stopName, pathDirection)
            }
        }
    }
}