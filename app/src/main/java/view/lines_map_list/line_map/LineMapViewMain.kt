package view.lines_map_list.line_map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.diplay.ouestmonbus.BuildConfig
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import model.DTO.Line
import model.DTO.Lines
import model.DTO.Networks
import model.DTO.Paths
import model.DTO.ProgrammedMessages
import model.DTO.Service
import model.DTO.Services
import model.preferences_data_store.StoreChosenNetwork
import model.preferences_data_store.StoreFavoriteLines
import view.advert_view.AdvertView
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LineMapViewMain(navController: NavController, lineId: String?) {
    val line = Lines.getLine((lineId ?: "0").toInt())
    val services = remember {
        mutableStateListOf<Service>()
    }
    val programmedMessagesCount = remember {
        mutableStateOf(0)
    }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val refreshDate = remember {
        mutableStateOf(Calendar.getInstance().time)
    }
    val selectedService = remember {
        mutableStateOf<Service?>(null)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(44.838670 - 0.06, -0.578620), 10.8f
        )
    }
    val isUserLocationShown = remember {
        mutableStateOf(false)
    }
    val userPosition = remember {
        mutableStateOf(LatLng(44.838670, -0.578620))
    }
    val pathsCoordinates = remember {
        mutableStateListOf<List<LatLng>>()
    }
    val colorScheme = !isSystemInDarkTheme()
    val context = LocalContext.current
    val storeChosenNetwork = StoreChosenNetwork(context)
    val network = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        storeChosenNetwork.chosenNetwork.collect { storedNetwork ->
            network.value = storedNetwork ?: ""
            Networks.getNetwork(storedNetwork ?: "")?.let { chosenNetwork ->
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(chosenNetwork.latitude - 0.06, chosenNetwork.longitude), 10.8f
                )
            }
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        BottomSheetScaffold(
            sheetContent = {
                LineMapViewBottomSheet(
                    services = services,
                    programmedMessagesCount = programmedMessagesCount.value,
                    isLoading = isLoading.value,
                    refreshDate = refreshDate.value,
                    selectedService = selectedService,
                    line = line,
                    cameraPositionState = cameraPositionState
                )
            },
            sheetBackgroundColor = if (colorScheme)
                Color.White.copy(alpha = 0.9f)
            else
                Color.Black.copy(alpha = 0.9f),
            sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            sheetElevation = 0.dp,
            sheetPeekHeight = if (BuildConfig.DEBUG) 75.dp else 125.dp,
            scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
            ),
            backgroundColor = if (colorScheme) Color.White else Color.Black
        ) { padding ->
            LaunchedEffect(lineId, network.value) {
                if(network.value == "tbm") {
                    ProgrammedMessages.getNumberOfMessagesByLine(line.id.toString()) { count ->
                        programmedMessagesCount.value = count
                    }
                }
                if(!line.isNest && network.value == "tbm") {
                    Paths.getOrderedPathsByLine("tbm", line.id, true) { paths ->
                        paths.forEach { backAndForthPaths ->
                            backAndForthPaths.forEach { path ->
                                pathsCoordinates.addAll(
                                    path.coordinates.map { coordinates ->
                                        coordinates.map { LatLng(it[1], it[0]) }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            LaunchedEffect(line, network.value) {
                if(network.value.isNotEmpty()) {
                    while(true) {
                        if(line.isNest) {
                            Lines.getChildLineIds(line.id) { childLineIds ->
                                Services.getServicesFilteredBy(network.value, childLineIds) { returnedServices ->
                                    services.clear()
                                    services.addAll(returnedServices)
                                    isLoading.value = false
                                    refreshDate.value = Calendar.getInstance().time
                                }
                            }
                        }
                        else {
                            Services.getServicesByLine(network.value, line.id) { returnedServices ->
                                services.clear()
                                services.addAll(returnedServices)
                                isLoading.value = false
                                refreshDate.value = Calendar.getInstance().time
                            }
                        }
                        delay(5000)
                    }
                }
            }

            Box(modifier = Modifier
                .padding(padding)
            ) {
                LineMapView(
                    services = services,
                    line = line,
                    selectedService = selectedService,
                    cameraPositionState = cameraPositionState,
                    isUserLocationShown = isUserLocationShown.value,
                    userPosition = userPosition.value,
                    pathsCoordinates = pathsCoordinates
                )

                LineMapViewTopBar(navController, line, isUserLocationShown, cameraPositionState, userPosition)
            }
        }

        if(!BuildConfig.DEBUG) {
            AdvertView(id = "8429467043", modifier = Modifier
                .align(Alignment.BottomCenter)
            )
        }
    }
}