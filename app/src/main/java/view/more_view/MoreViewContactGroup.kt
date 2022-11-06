package view.more_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoreViewContactGroup() {
    Column {
        Text("Contacter le développeur", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier
            .padding(start = 15.dp)
        )

        MoreViewContactMailRow()

        Spacer(modifier = Modifier
            .height(10.dp)
        )

        MoreViewContactTwitterRow()
    }
}