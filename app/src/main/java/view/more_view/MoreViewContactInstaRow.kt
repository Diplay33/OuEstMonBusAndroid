package view.more_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
fun MoreViewContactInstaRow() {
    val uriHandler = LocalUriHandler.current

    Row(modifier = Modifier
        .height(45.dp)
        .fillMaxWidth()
        .clickable {
            uriHandler.openUri("https://www.instagram.com/ou_est_mon_bus/")
        }
        .padding(horizontal = 15.dp)
    ) {
        Row(Modifier
            .align(Alignment.CenterVertically)
        ) {
            Image(painterResource(id = R.drawable.instagram), contentDescription = null, modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier
                .width(15.dp)
            )

            Text(
                text = "Où est mon bus (@ou_est_mon_bus)",
                color = Color(android.graphics.Color.parseColor("#007AFF")),
                fontSize = 18.sp
            )
        }
    }
}