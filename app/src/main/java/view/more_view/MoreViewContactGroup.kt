package view.more_view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun MoreViewContactGroup() {
    val colorScheme = !isSystemInDarkTheme()

    Column {
        Text(
            text = "Contacter le développeur",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = if (colorScheme) Color.Black else Color.White,
            modifier = Modifier
                .padding(start = 15.dp)
                .padding(bottom = 3.dp)
        )

        Column(Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
            .background(
                if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
                shape = RoundedCornerShape(10.dp)
            )
        ) {
            MoreViewContactMailRow()

            Box(modifier = Modifier
                .padding(start = 50.dp)
                .clip(RectangleShape)
                .background(if (colorScheme)
                    Color.LightGray
                else
                    Color.Gray
                )
                .fillMaxWidth()
                .height(1.dp)
            )

            MoreViewContactTwitterRow()

            Box(modifier = Modifier
                .padding(start = 50.dp)
                .clip(RectangleShape)
                .background(if (colorScheme)
                    Color.LightGray
                else
                    Color.Gray
                )
                .fillMaxWidth()
                .height(1.dp)
            )

            MoreViewContactInstaRow()
        }
    }
}