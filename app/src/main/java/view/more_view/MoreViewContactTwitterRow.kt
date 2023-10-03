package view.more_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.R

@Composable
fun MoreViewContactTwitterRow() {
    val uriHandler = LocalUriHandler.current
    val colorScheme = !isSystemInDarkTheme()

    Row(modifier = Modifier
        .height(45.dp)
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.x),
                contentDescription = null,
                colorFilter = ColorFilter.tint(if (colorScheme)
                    Color.Black
                else
                    Color.White),
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            Text(text = "Jacques HU (@diplay3311)",
                fontSize = 18.sp,
                color = Color.Blue,
                modifier = Modifier
                    .clickable {
                        uriHandler.openUri("https://x.com/diplay3311")
                    }
            )
        }
    }
}