package app.covidstats.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import app.covidstats.ui.theme.Green20
import app.covidstats.ui.theme.Green30

/**
 * Draws a title with predefined attributes.
 * @param textAlign can be used to aline the title.
 */
@Composable
internal fun Title(title: String, modifier: Modifier = Modifier, textAlign: TextAlign? = null, fontColor: Color = MaterialTheme.colorScheme.primary) {
    val configuration = LocalConfiguration.current
    val fontSize =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 8.em
        else 10.em
    val lineHeight =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 8.em
        else 10.em
    val buttonPadding =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 30.dp
        else 20.dp
    Text(
        text = title,
        color = fontColor,
        lineHeight = lineHeight,
        fontSize = fontSize,
        //fontFamily = FontFamily(Font(R.font.my_type, weight = FontWeight.Normal)),
        fontWeight = FontWeight.Bold,
        //style = TextStyle(textDecoration = TextDecoration.Underline),
        textAlign = textAlign,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, bottom = buttonPadding),
    )
}