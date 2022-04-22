package app.covidstats.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.R

/**
 * Draws a title with predefined attributes.
 * @param textAlign can be used to aline the title.
 */
@Composable
internal fun Title(title: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null, fontColor: Color = Color.Black) {
    Text(
        text = title,
        color = fontColor,
        fontSize = 35.sp,
        fontFamily = FontFamily(Font(R.font.my_type, weight = FontWeight.Normal)),
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = (if (textAlign == null) 15.dp else 0.dp), top = 30.dp,  bottom = 30.dp),
    )
    Spacer(modifier = Modifier.height(10.dp))
}