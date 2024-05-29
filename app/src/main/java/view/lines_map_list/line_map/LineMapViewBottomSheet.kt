package view.lines_map_list.line_map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.CameraPositionState
import model.DTO.LineR
import model.DTO.Service
import view.lines_map_list.line_map.messages_list.LineMapViewProgrammedMessagesViewMain
import view.lines_map_list.line_map.service_detail.LineMapViewServiceDetail
import java.util.Date

@Composable
fun LineMapViewBottomSheet(
    services: List<Service>,
    programmedMessagesCount: Int,
    isLoading: Boolean,
    refreshDate: Date,
    selectedService: MutableState<Service?>,
    line: LineR?,
    cameraPositionState: CameraPositionState
) {
    val areMessagesDisplayed = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(LocalConfiguration.current.screenHeightDp.dp / 2 - 50.dp)
    ) {
        Spacer(modifier = Modifier
            .height(10.dp)
        )

        Box(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray)
            .width(40.dp)
            .height(5.dp)
            .align(Alignment.CenterHorizontally)
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        if(areMessagesDisplayed.value) {
            LineMapViewProgrammedMessagesViewMain(line, areMessagesDisplayed)
        }
        else {
            if(selectedService.value == null) {
                LineMapViewServicesList(
                    services = services,
                    programmedMessagesCount = programmedMessagesCount,
                    refreshDate = refreshDate,
                    selectedService = selectedService,
                    areMessagesDisplayed = areMessagesDisplayed,
                    isLoading = isLoading,
                    cameraPositionState = cameraPositionState
                )
            }
            else {
                LineMapViewServiceDetail(selectedService, line)
            }
        }
    }
}