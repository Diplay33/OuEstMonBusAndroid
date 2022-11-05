package view.lines_map_list.line_map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.DTO.Lines

@Composable
fun HelloWorld(lineId: String?) {
    val line = Lines.getLine(lineId)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
    ) {
        Text("Hello World! ${line?.lineName}", textAlign = TextAlign.Center, modifier = Modifier
            .padding(vertical = 10.dp)
        )
    }
}