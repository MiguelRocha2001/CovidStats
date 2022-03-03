package app.covidstats.Ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Draws a title with predefined attributes.
 * @param textAlign can be used to aline the title.
 */
@Composable
internal fun Title(title: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null) {
    Text(
        text = title,
        fontSize = 31.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = (if (textAlign == null) 15.dp else 0.dp), top = 30.dp,  bottom = 30.dp),
    )
    Spacer(modifier = Modifier.height(10.dp))
}