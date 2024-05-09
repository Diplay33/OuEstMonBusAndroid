package view.next_schedules.favorite_stop_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import model.DTO.DestinationsAller
import model.DTO.DestinationsRetour
import model.DTO.Lines
import model.DTO.NSchedulesMapMarker
import model.DTO.NSchedulesMapMarkerType
import model.DTO.NSchedulesMapMarkers
import model.DTO.NextSchedule
import model.DTO.NextSchedules
import model.DTO.Path
import model.DTO.Paths
import model.DTO.Station
import model.DTO.Stations
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesHeader
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesMap
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesSchdlGroup
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesTopBar
import view.next_schedules.search_line.search_stop_list.next_line_schedules.NextLineSchedulesView

@Composable
fun FavoriteStopDetailMain(
    navController: NavController,
    stopId: String?,
    stopName: String?,
    lineId: String?
) {
    val colorScheme = !isSystemInDarkTheme()
    val line = Lines.getLine(lineId)
    val paths = remember {
        mutableStateListOf<Path>()
    }
    val destinations = remember {
        mutableStateListOf<List<String>>()
    }
    val capFilteredNextSchedules = remember {
        mutableStateListOf<NextSchedule>()
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val station = remember {
        mutableStateOf<Station?>(null)
    }
    val stationMarker = remember {
        mutableStateOf(NSchedulesMapMarker(NSchedulesMapMarkerType.STOP, null, null))
    }
    val mapMarkers = remember {
        mutableStateListOf<NSchedulesMapMarker>()
    }
    val pathDirection = remember {
        mutableStateOf("")
    }
    val focusedVehicle = remember {
        mutableStateOf<Int?>(null)
    }
    val pathsCoordinates = remember {
        mutableStateListOf<List<LatLng>>()
    }

    LaunchedEffect(stopName) {
        Paths.getOrderedPathsByLine(line.id) { orderedPaths ->
            orderedPaths.forEach { returnedPaths ->
                Stations.getSortedStationsByPaths(returnedPaths) { stations ->
                    if(stations.map { it.stationId }.contains(stopId.toString())) {
                        paths.clear()
                        paths.addAll(returnedPaths)
                        destinations.clear()
                        pathDirection.value = paths.first().direction
                        destinations.addAll(if (pathDirection.value == "ALLER")
                            DestinationsAller.getDestinationAllerOfLine(line.id)
                        else
                            DestinationsRetour.getDestinationRetourOfLine(line.id))

                    }
                }
            }
        }
        Stations.getStationByStationId(stopId.toString()) { returnedStation ->
            station.value = returnedStation
            stationMarker.value = NSchedulesMapMarker(NSchedulesMapMarkerType.STOP, returnedStation, null)
            mapMarkers.add(stationMarker.value)
        }
        while(true) {
            NextSchedules.getNextSchedulesByStationId(stopId.toString()) { nextSchedules ->
                capFilteredNextSchedules.clear()
                nextSchedules.forEach { ns ->
                    if(capFilteredNextSchedules.size < 5 && ns.lineId == line.id) {
                        capFilteredNextSchedules.add(ns)
                    }
                }
                isLoading.value = false

                NSchedulesMapMarkers.retrieveVehicles(line.id, capFilteredNextSchedules.map { it.vehicleId ?: 0 }) { vehicles ->
                    mapMarkers.clear()
                    mapMarkers.addAll(vehicles + listOf(stationMarker.value))
                }
            }
            delay(10000)
        }
    }

    LaunchedEffect(pathDirection.value) {
        if(pathDirection.value != "") {
            Paths.getOrderedPathsByLine(line.id, true) { returnedPaths ->
                returnedPaths[if (pathDirection.value == "ALLER") 0 else 1].forEach { path ->
                    pathsCoordinates.addAll(
                        path.coordinates.map { coordinates ->
                            coordinates.map { LatLng(it[1], it[0]) }
                        }
                    )
                }
            }
        }
    }

    Scaffold(topBar = {
        NextLineSchedulesTopBar(navController, stopId.toString(), stopName.toString(), line)
    }) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .fillMaxHeight()
            .background(if (colorScheme) Color.White else Color.Black)
        ) {
            item {
                NextLineSchedulesHeader(line, paths, destinations)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextLineSchedulesView(capFilteredNextSchedules, line, isLoading.value, focusedVehicle)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextLineSchedulesMap(station.value, line, mapMarkers, focusedVehicle, navController, pathsCoordinates)

                Spacer(modifier = Modifier
                    .height(30.dp)
                )

                NextLineSchedulesSchdlGroup(navController, line, stopId, stopName, pathDirection.value)
            }
        }
    }
}