package view.more_view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoreViewContactMailRow() {
    val context = LocalContext.current
    val colorScheme = !isSystemInDarkTheme()

    Row(modifier = Modifier
        .height(45.dp)
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 15.dp)
            .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Rounded.Email,
                contentDescription = null,
                tint = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            Text(
                text = "ouestmonbusoff@gmail.com",
                fontSize = 18.sp,
                color = Color(android.graphics.Color.parseColor("#007AFF")),
                modifier = Modifier
                    .clickable {
                        context.sendMail(to = "ouestmonbusoff@gmail.com")
                    }
            )
        }
    }
}

fun Context.sendMail(to: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$to")
    }
    startActivity(intent)
}