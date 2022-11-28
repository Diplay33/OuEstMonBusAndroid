package view.next_schedules.search_line.search_stop_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Line

@Composable
fun SearchStopListHeader(line: Line) {
    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
        .background(
            colorResource(id = line.lineColorResource).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ) {
            Image(
                painter = painterResource(id = line.lineImageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = line.lineName,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(vertical = 13.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}