package view.more_view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.BuildConfig

@Composable
fun MoreViewAppInfoGroup() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp)
    ) {
        Text(
            text ="©2022 - 2024 Jacques HU",
            fontSize = 18.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Cette application n'est ni affiliée à TBM," +
                " ni à Keolis Bordeaux Métropole Mobilités.",
            fontSize = 18.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier
            .height(30.dp)
        )

        Text(
            text = "Données fournies par Bordeaux Métropole, via leur programme OpenData.",
            fontSize = 18.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Où est mon bus se dédouane de toute responsabilité concernant d'éventuelles " +
                    "données erronées.",
            fontSize = 18.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Text(
            text = "v${BuildConfig.VERSION_NAME} b${BuildConfig.VERSION_CODE}",
            fontSize = 18.sp,
            color = Color(0xffE3E3E3),
            textAlign = TextAlign.Center
        )

    }
}