package view.lines_map_list.line_map.messages_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DTO.ProgrammedMessage
import java.time.format.DateTimeFormatter

@Composable
fun LineMapViewProgrammedMessagesViewRow(programmedMessage: ProgrammedMessage) {
    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .fillMaxWidth()
        .padding(bottom = 15.dp)
    ) {
        Column {
            Column(modifier = Modifier
                .background(
                    Color(0xffF5F5F5),
                    shape = RoundedCornerShape(10.dp)
                )
            ) {
                Text(
                    text = programmedMessage.title.replace("\\s+".toRegex(), " "),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 7.dp)
                        .padding(horizontal = 15.dp)
                )

                Row {
                    Spacer(modifier = Modifier
                        .width(15.dp)
                    )

                    Box(modifier = Modifier
                        .clip(RectangleShape)
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .height(1.dp)
                    )
                }

                Text(
                    text = programmedMessage.bodyMessage.replace("\\s+".toRegex(), " "),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(vertical = 7.dp)
                        .padding(horizontal = 15.dp)
                )
            }

            Text(
                text = "Mis à jour le ${
                    programmedMessage.lastUpdated.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                } à ${
                    programmedMessage.lastUpdated.format(DateTimeFormatter.ofPattern("HH:mm"))
                }",
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
        }
    }
}