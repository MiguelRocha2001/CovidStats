package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.Model
import app.covidstats.model.data.covid_stats.Continent
import app.covidstats.model.data.covid_stats.Country
import app.covidstats.model.data.covid_stats.World
import java.util.*
import kotlin.reflect.full.memberProperties

val AMBAR_LIGHT = Color(225, 172, 27)
val AMBAR_DARK = Color(160, 122, 19)
val BLUE_LIGHT = Color(0,179, 179)
val BLUE_DARK = Color(0,102, 102)
val GREY = Color(128,128, 128)
val LIGHT_GREY = Color(179,179, 179)
val DARK_GREY = Color(89,80, 89)
val SEA_BLUE = Color(0,153, 255)
val AQUA_BLUE = Color(0,255, 255)
val STRONG_GREEN = Color(0,153, 0)

@Composable
fun ShowWorldCovidStats(model: Model?) {
    if (model != null) {
        val covidStats = model.stats
        val location = model.location
        covidStats?:return
        location?:return
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        ) {
            Text(
                text = "${location.capitalizeText()} Covid-19 Stats",
                fontSize = 31.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = AMBAR_DARK
            )
            Spacer(modifier = Modifier.height(20.dp))
            // depending on what type of data is to be displayed
            when (val data = model.stats) {
                is World -> displayWorldData(data)
                is Continent -> displayContinentData(data)
                is Country -> displayCountryData(data)
            }
        }
    }
}

@Composable
private fun displayWorldData(data: World) {
    for (prop in World::class.memberProperties)
        PrintText(prop.name, prop.get(data))
}

@Composable
private fun displayContinentData(data: Continent) {
    for (prop in Continent::class.memberProperties)
        PrintText(prop.name, prop.get(data))
}

@Composable
private fun displayCountryData(data: Country) {
    for (prop in Country::class.memberProperties)
        PrintText(prop.name, prop.get(data))
}

@Composable
fun PrintText(str: String, value: Any?) {
    Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
        Text(text = "$str ", fontSize = 20.sp, color = STRONG_GREEN, fontWeight = Bold)
        Text(text = if (value != null) format(value) else "in fault", fontSize = 20.sp, color = GREY)
    }
}

private fun String.capitalizeText(): String {
    val str = this
    val wordsBeforeCapitalized = str.split(" ")
    val wordsAfterCapitalized = wordsBeforeCapitalized.map { word -> word.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }}
    return wordsAfterCapitalized.joinToString (separator = " ")
}

/**
 * Converts numbers (Int, Long, Float and Double) to a formatted String.
 * If its not one of the above types, just uses implicit toString.
 */
private fun format(number: Any): String {
    if (number !is Long && number !is Double && number !is Int && number !is Float)
        return number.toString()
    val str = number.toString()
    val hasDecimal = number is Float || number is Double
    val integer = str.subSequence(0, if (hasDecimal) str.indexOf('.') else str.length)
    var integerRes = ""
    // formats integer part
    for (i in integer.indices.reversed())
        integerRes += if ((integer.length - i) % 3 == 0) "${integer[i]} " else integer[i]
    integerRes = integerRes.reversed()
    if (!hasDecimal)
        return integerRes
    // formats decimal part
    val decimal = str.subSequence(str.indexOf('.')+1, str.length)
    var decimalRes = ""
    for (i in decimal.indices.reversed())
        decimalRes += if ((decimal.length - i) % 3 == 0) "${decimal[i]} " else decimal[i]
    return "$integerRes.${decimalRes.reversed()}"

}