package view.more_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ouestmonbus.R

@Composable
fun MoreViewContactTwitterRow() {
    val uriHandler = LocalUriHandler.current

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .fillMaxWidth()
        .background(
            Color(0xffF5F5F5),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 4.dp)
        ) {
            Image(painterResource(id = R.drawable.twitter), contentDescription = null, modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(25.dp)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            Text("Jacques HU (@diplay3311)", fontSize = 18.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    uriHandler.openUri("https://twitter.com/diplay3311")
                }
            )

            Spacer(modifier = Modifier
                .height(35.dp)
            )
        }
    }
}