package view.helpers

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    fontSizeRange: ClosedFloatingPointRange<Float> = 12f..20f, // Minimum and maximum font sizes
    maxLines: Int = 1,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    color: Color = Color.Unspecified
) {
    var textSize by remember { mutableStateOf(fontSizeRange.endInclusive) }
    Box(modifier = modifier) {
        Text(
            text = text,
            maxLines = maxLines,
            style = TextStyle(fontSize = textSize.sp),
            fontWeight = fontWeight,
            textAlign = textAlign,
            color = color,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow && textSize > fontSizeRange.start) {
                    textSize -= 1f // Gradually reduce font size
                }
            }
        )
    }
}
