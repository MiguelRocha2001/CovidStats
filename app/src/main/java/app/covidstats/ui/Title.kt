package app.covidstats.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Draws a title with predefined attributes.
 * @param textAlign can be used to aline the title.
 */
@Composable
internal fun Title(title: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null, fontColor: Color = BLUE) {
    Text(
        text = title,
        color = fontColor,
        fontSize = 40.sp,
        //fontFamily = FontFamily(Font(R.font.my_type, weight = FontWeight.Normal)),
        fontWeight = FontWeight.Bold,
        //style = TextStyle(textDecoration = TextDecoration.Underline),
        textAlign = textAlign,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp,  bottom = 50.dp),
    )
}