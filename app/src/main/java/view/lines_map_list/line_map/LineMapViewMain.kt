package view.lines_map_list.line_map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Lines
import model.DTO.Service
import model.DTO.Services

@Composable
fun LineMapViewMain(navController: NavController, lineId: String?) {
    val line = Lines.getLine(lineId)
    val services = remember {
        mutableStateListOf<Service>()
    }

    Scaffold { padding ->
        LaunchedEffect(line) {
            while(true) {
                if(lineId == "132") {
                    Services.getNavetteTramServices { returnedServices ->
                        services.clear()
                        services.addAll(returnedServices)
                    }
                }
                else {
                    Services.getServicesByLine(line.id) { returnedServices ->
                        services.clear()
                        services.addAll(returnedServices)
                    }
                }
                delay(2000)
            }
        }

        Box(modifier = Modifier
            .padding(padding)
        ) {
            LineMapView(services, line.lineName)

            LineMapViewTopBar(navController, line)
        }
    }
}