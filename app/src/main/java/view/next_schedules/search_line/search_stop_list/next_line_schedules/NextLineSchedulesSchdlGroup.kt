package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import model.DTO.Line
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NextLineSchedulesSchdlGroup(
    navController: NavController,
    line: Line,
    stopId: String?,
    stopName: String?,
    pathDirection: String?
    ) {
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd MMM yyyy")
                .format(selectedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val colorScheme = !isSystemInDarkTheme()

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
    ) {
        Text(
            text = "Horaires",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (colorScheme) Color.Black else Color.White,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )

        Box(modifier = Modifier
            .background(
                if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .align(Alignment.CenterVertically)
            .clickable {
                dateDialogState.show()
            }
        ) {
            Text(
                text = formattedDate, fontSize = 18.sp,
                color = if (colorScheme) Color.Black else Color.White,
            )
        }
    }

    NextLineSchedulesButton(line, navController, stopId, stopName, pathDirection, selectedDate)

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK", textStyle = TextStyle(color = colorResource(line.lineColorResource), fontWeight = FontWeight.Bold))
            negativeButton("Annuler", textStyle = TextStyle(color = colorResource(line.lineColorResource), fontWeight = FontWeight.Bold))
        },
        shape = RoundedCornerShape(5),
        backgroundColor = if (colorScheme) Color.White else Color(0xff18191A)
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = colorResource(line.lineColorResource),
                dateActiveBackgroundColor = colorResource(line.lineColorResource),
                calendarHeaderTextColor = if (colorScheme) Color.Black else Color.White,
                dateInactiveTextColor = if (colorScheme) Color.Black else Color.White
            ),
            allowedDateValidator = {
                it.isBefore(LocalDate.now().plusDays(14)) && it.isAfter(LocalDate.now().minusDays(1))
            }
        ) {
            selectedDate = it
        }
    }
}