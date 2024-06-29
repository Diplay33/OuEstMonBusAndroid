package view.whats_new_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.BuildConfig
import com.diplay.ouestmonbus.R

@Composable
fun WhatsNewViewMain() {
    Column {
        Column {
            Spacer(modifier = Modifier
                .height(50.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            spotColor = Color.Black.copy(0.5f),
                            ambientColor = Color.Black.copy(0.1f),
                            shape = RoundedCornerShape(35.dp)
                        )
                        .clip(RoundedCornerShape(35.dp))
                        .size(150.dp)
                )

                Text(
                    text = "Quoi de neuf dans la version ${BuildConfig.VERSION_NAME} ?",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 15.dp)
                )
            }
        }
    }
}