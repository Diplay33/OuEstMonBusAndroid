package view.more_view.all_services_list.service_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import model.DTO.Line
import model.DTO.LineR

@Composable
fun ServiceDetailHeader(line: LineR?, destination: List<String>) {
    val colorScheme = !isSystemInDarkTheme()

    Row(modifier = Modifier
        .padding(horizontal = 15.dp)
        .fillMaxWidth()
        .background(
            if (colorScheme) Color.Transparent else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
        .background(
            color = if (line == null)
                Color.Transparent
            else
                Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
        .padding(vertical = 3.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(line?.imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 15.dp)
                .width(70.dp)
                .height(55.dp)
                .align(Alignment.CenterVertically)
        )

        Column(modifier = Modifier
            .padding()
            .offset(y = 3.dp)
        ) {
            Text(
                text = line?.name ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )

            Row(modifier = Modifier
                .offset(y = (-5).dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = if (colorScheme) Color.Black else Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .offset(x = 4.dp)
                )

                Column(modifier = Modifier
                    .padding(horizontal = 10.dp)
                ) {
                    if(destination.first() != "") {
                        Text(
                            text = destination.first(),
                            fontSize = 13.sp,
                            color = Color.Gray,
                            modifier = Modifier
                                .offset(y = if (destination.first() == "") 0.dp else 2.dp)
                        )

                        Spacer(modifier = Modifier.height(3.dp))
                    }

                    Text(
                        text = destination.last(),
                        fontSize = 18.sp,
                        color = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .offset(y = if (destination.first() == "") 0.dp else (-2).dp)
                    )
                }
            }
        }
    }
}