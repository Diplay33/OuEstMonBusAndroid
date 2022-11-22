package view.lines_map_list.line_map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.DTO.Lines
import model.DTO.Service
import model.DTO.Services

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LineMapViewMain(navController: NavController, lineId: String?) {
    val line = Lines.getLine(lineId)
    val services = remember {
        mutableStateListOf<Service>()
    }
    
    BottomSheetScaffold(
        sheetContent = {
            LineMapViewBottomSheet(services)
        },
        sheetBackgroundColor = Color.White.copy(alpha = 0.8f),
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
    ) { padding ->
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