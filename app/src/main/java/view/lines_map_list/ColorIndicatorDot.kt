package view.lines_map_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun ColorIndicatorDot(color: Color, size: Dp, modifier: Modifier) {
    Box(modifier = Modifier
        .size(size)
        .clip(CircleShape)
        .background(color)
        .then(modifier)
    )
}