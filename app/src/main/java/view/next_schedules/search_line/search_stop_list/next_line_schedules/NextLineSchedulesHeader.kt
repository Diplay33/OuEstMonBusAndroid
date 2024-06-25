package view.next_schedules.search_line.search_stop_list.next_line_schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import model.DTO.Line
import model.DTO.Path

@Composable
fun NextLineSchedulesHeader(
    line: Line?,
    paths: List<Path>,
    destinations: List<List<String>> = listOf()
) {
    val destinationsSet = remember {
        mutableSetOf<String>()
    }
    paths.forEach { destinationsSet.add(it.getDestinationName()) }
    val colorScheme = !isSystemInDarkTheme()

    Column(modifier = Modifier
        .padding(horizontal = 15.dp)
        .background(
            if (colorScheme) Color.Transparent else Color(0xff18191A),
            shape = RoundedCornerShape(10.dp)
        )
        .background(
            if (line == null)
                Color.Transparent
            else
                Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(line?.imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = line?.name ?: "",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = if (colorScheme) Color.Black else Color.White,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(vertical = 13.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Column(modifier = Modifier
            .background(
                if (line == null)
                    Color.Transparent
                else
                    Color(android.graphics.Color.parseColor(line.colorHex)).copy(alpha = 0.2f),
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
            )
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
        ) {
            Row(modifier = Modifier
                .background(
                    if (colorScheme)
                        Color.White.copy(alpha = 0.4f)
                    else
                        Color(0xff18191A).copy(alpha = 0.4f),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(start = 15.dp)
                .padding(vertical = 2.dp)
                .fillMaxWidth()
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        tint = if (colorScheme) Color.Black else Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .offset(x = (-7).dp)
                    )

                    if(destinations.isEmpty()) {
                        if(destinationsSet.isEmpty()) {
                            CircularProgressIndicator(modifier = Modifier
                                .size(20.dp)
                            )
                        }
                        else {
                            Column(modifier = Modifier
                                .align(Alignment.CenterVertically)
                            ) {
                                destinationsSet.forEach { destination ->
                                    Text(
                                        text = destination,
                                        fontSize = 18.sp,
                                        color = if (colorScheme) Color.Black else Color.White,
                                        modifier = Modifier
                                            .padding(vertical = 8.dp)
                                            .padding(end = 15.dp)
                                            .fillMaxWidth(fraction = 0.85f)
                                    )

                                    if(destination != destinationsSet.last()) {
                                        Box(modifier = Modifier
                                            .clip(RectangleShape)
                                            .background(if (colorScheme)
                                                Color.LightGray
                                            else
                                                Color.Gray
                                            )
                                            .fillMaxWidth()
                                            .height(1.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Column(modifier = Modifier
                            .align(Alignment.CenterVertically)
                        ) {
                            destinations.forEach { destination ->
                                Column {
                                    Text(
                                        text = destination.first(),
                                        fontSize = 13.sp,
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .offset(y = 2.dp)
                                    )

                                    Spacer(modifier = Modifier.height(3.dp))

                                    Text(
                                        text = destination.last(),
                                        fontSize = 18.sp,
                                        color = if (colorScheme) Color.Black else Color.White,
                                        modifier = Modifier
                                            .offset(y = (-2).dp)
                                    )
                                }

                                if(destination != destinations.last()) {
                                    Box(modifier = Modifier
                                        .clip(RectangleShape)
                                        .background(if (colorScheme)
                                            Color.LightGray
                                        else
                                            Color.Gray
                                        )
                                        .fillMaxWidth()
                                        .height(1.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier
                .height(15.dp)
            )
        }
    }
}