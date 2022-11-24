package view.lines_map_list.line_map.messages_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.Line
import model.DTO.ProgrammedMessage
import model.DTO.ProgrammedMessages

@Composable
fun LineMapViewProgrammedMessagesViewMain(line: Line, areMessagesDisplayed: MutableState<Boolean>) {
    val programmedMessages = remember {
        mutableStateListOf<ProgrammedMessage>()
    }

    LaunchedEffect(line) {
        ProgrammedMessages.getProgrammedMessagesByLine(line.id.toString()) {
            programmedMessages.clear()
            programmedMessages.addAll(it)
        }
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = "Messages " + when(line.lineName) {
                "BatCUB", "Tram A", "Tram B", "Tram C", "Tram D" -> "du ${line.lineName}"
                else -> "de la ${line.lineName}"
            },
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically)
        )

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(end = 15.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .size(35.dp)
            .clickable {
                areMessagesDisplayed.value = false
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null
            )
        }
    }

    Spacer(modifier = Modifier
        .height(10.dp)
    )

    LazyColumn {
        items(programmedMessages) { programmedMessage ->
            LineMapViewProgrammedMessagesViewRow(programmedMessage)
        }
    }
}