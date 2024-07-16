package view.whats_new_view.network_picker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplay.ouestmonbus.R
import model.DTO.Network

@Composable
fun NetworkPickerRow(selection: MutableState<String>, network: Network) {
    val colorScheme = !isSystemInDarkTheme()
    val isSelected = network.shortName == selection.value

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .shadow(
                elevation = 3.dp,
                spotColor = Color.Black.copy(0.5f),
                ambientColor = Color.Black.copy(if (isSelected) 0.8f else 0.2f),
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = (if (isSelected) 5 else -1).dp,
                color = Color(android.graphics.Color.parseColor(network.tintColorHex)),
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .background(
                if (colorScheme)
                    colorResource(id = R.color.light_grey)
                else
                    Color.DarkGray
            )
            .clickable { selection.value = network.shortName }
            .padding(vertical = 8.dp)
            .padding(horizontal = 15.dp)
    ) {
        Column(modifier = Modifier
            .weight(0.5f, fill = true)
        ) {
            Text(
                text = computeNetworkDescription(network),
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier

            )

            Text(
                text = network.fullName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                color = if (colorScheme) Color.Black else Color.White
            )
        }

        Spacer(modifier = Modifier
            .size(8.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = computeNetworkImage(network)),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(80.dp)
            )

            Spacer(modifier = Modifier
                .width(8.dp)
            )

            Box(contentAlignment = Alignment.Center) {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .size(20.dp)
                    .background(Color(android.graphics.Color.parseColor(network.tintColorHex)))
                )
                
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .size(14.dp)
                    .background(
                        if (colorScheme)
                            colorResource(id = R.color.light_grey)
                        else
                            Color.DarkGray
                    )
                )

                if(isSelected) {
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .size(10.dp)
                        .background(Color(android.graphics.Color.parseColor(network.tintColorHex)))
                    )
                }
            }
        }
    }
}

fun computeNetworkDescription(network: Network): String {
    when(network.shortName) {
        "tbm" -> return "MÉTROPOLE BORDELAISE"
        "ametis" -> return "COMMUNAUTÉ D'AGGLO. AMIÉNOISE"
        "star" -> return "MÉTROPOLE RENNAISE"
    }
    return ""
}

fun computeNetworkImage(network: Network): Int {
    when(network.shortName) {
        "tbm" -> return R.drawable.tbm
        "ametis" -> return R.drawable.ametis
        "star" -> return R.drawable.star
    }
    return R.drawable.question_mark_box
}