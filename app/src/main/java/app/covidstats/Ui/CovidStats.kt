package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.R
import app.covidstats.model.Model
import app.covidstats.model.data.covid_stats.CovidStats
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
// ------------------------------------------------
val GREEN = Color(115,153, 0)
val ORANGE = Color(204,102, 0)
val RED = Color(179,0, 0)
val YELLOW = Color(204,204, 0)
val STRONG_BLUE = Color(0,89, 179)
val LIGHT_BLUE = Color(0,153, 153)

@Composable
fun CovidStats(model: Model?) {
    if (model != null) {
        val covidStats = model.stats
        covidStats?:return
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        ) {
            // draws location that stats represents
            Title(title = "${covidStats.first.capitalizeText()} Covid-19 Stats")
            // depending on what type of data is to be displayed
            model.stats?.second?.apply {
                Data(data = this)
            }
        }
    }
}

@Composable
private fun Data(data: CovidStats) {
    val (mainSats, otherStats) = separateStats(data)
    Column() {
        Box(Modifier.padding(10.dp)) {
            Column() {
                mainSats.forEach { (name, value) ->
                    StatLine(name, value)
                    Spacer(Modifier.height(15.dp))
                }
            }
        }
        Box(Modifier.padding(10.dp)) {
            Column() {
                otherStats.forEach { (name, value) ->
                    StatLine(name, value)
                }
            }
        }
    }
}

/**
 * Displays a single stat line.
 */
@Composable
fun StatLine(str: String, value: Any?) {
    Row(Modifier.padding(start = 10.dp)) {
        val color =
            if (str == "cases" || str == "casesPerOneMillion") GREEN
            else if (str == "deaths" || str == "deathsPerOneMillion") RED
            else if (str == "tests" || str == "testsPerOneMillion") ORANGE
            else if (str == "active" || str == "activePerOneMillion") YELLOW
            else if (str == "recovered" || str == "recoveredPerOneMillion") STRONG_BLUE
            else if (str == "population") LIGHT_BLUE
            else Color.Black
        val font = FontFamily(Font(R.font.my_type, weight = FontWeight.Normal))
        val fontSize = 20.sp
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${str.capitalizeText()} ",
                fontSize = fontSize,
                color = color,
                fontFamily = font,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
            Text(
                text = if (value != null) format(value) else "in fault",
                fontSize = fontSize,
                color = color,
                fontFamily = font
            )
        }
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

/**
 * @return a Pair type separating the main stats from the secondary ones.
 */
private fun separateStats(data: CovidStats): Pair<List<Pair<String, Any?>>, List<Pair<String, Any?>>> {
    val list1 = mutableListOf<Pair<String, Any?>>()
    val list2 = mutableListOf<Pair<String, Any?>>()
    for (prop in CovidStats::class.memberProperties) {
        val str = prop.name
        if (
            str == "cases" || str == "casesPerOneMillion" || str == "deaths" || str == "deathsPerOneMillion" ||
            str == "tests" || str == "testsPerOneMillion" || str == "active" || str == "activePerOneMillion" ||
            str == "recovered" || str == "recoveredPerOneMillion" || str == "population"
        )
            list1.add(str to prop.get(data))
        else
            list2.add(str to prop.get(data))
    }
    return list1 to list2
}