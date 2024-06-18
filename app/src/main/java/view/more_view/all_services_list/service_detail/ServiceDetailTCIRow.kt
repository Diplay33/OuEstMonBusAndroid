package view.more_view.all_services_list.service_detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.diplay.ouestmonbus.R
import view.helpers.openTab

@Composable
fun ServiceDetailTCIRow(tciId: Int) {
    val colorScheme = !isSystemInDarkTheme()
    val context = LocalContext.current

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .clickable { openTab("https://tc-infos.fr/id/$tciId", context) }
        .height(45.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color(0xffF5F5F5) else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
            .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Afficher dans TC Infos",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(android.graphics.Color.parseColor("#007AFF"))
            )
        }
    }
}