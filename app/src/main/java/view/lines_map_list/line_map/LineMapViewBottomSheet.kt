package view.lines_map_list.line_map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Service
import java.util.Date

@Composable
fun LineMapViewBottomSheet(
    services: List<Service>,
    programmedMessagesCount: Int,
    isLoading: Boolean,
    refreshDate: Date,
    selectedService: MutableState<Service?>
) {
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

        if(selectedService.value == null) {
            if(isLoading) {
                Row(
                    horizontalArrangement = Arrangement.Center, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            else {
                LineMapViewServicesList(
                    services = services,
                    programmedMessagesCount = programmedMessagesCount,
                    refreshDate = refreshDate,
                    selectedService = selectedService
                )
            }
        }
        else {
            LineMapViewServiceDetail(selectedService)
        }
    }
}