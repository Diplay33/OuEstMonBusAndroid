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
        .padding(horizontal = 15.dp)
        .height(45.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .align(Alignment.CenterVertically)
        ) {
            Image(painterResource(id = R.drawable.twitter), contentDescription = null, modifier = Modifier
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
                        uriHandler.openUri("https://twitter.com/diplay3311")
                    }
            )
        }
    }
}