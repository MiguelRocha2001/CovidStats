package app.covidstats.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.R
import app.covidstats.model.Model
import app.covidstats.model.data.covid_stats.CovidStats
import java.util.*
import kotlin.reflect.full.memberProperties

@Composable
fun CovidStats(model: Model?, onFavoriteAdd: (String) -> Unit, onFavoriteRemove: (String) -> Unit) {
    if (model != null) {
        val covidStats = model.stats
        if (covidStats == null)
            LoadingPage()
        else {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
            ) {
                Title(title = "${covidStats.first.capitalizeText()} Covid-19 Stats")
                // depending on what type of data is to be displayed
                model.stats?.second?.apply {
                    Data(data = this)
                }
                Spacer(modifier = Modifier.height(15.dp))
                Favorite(model, onFavoriteAdd, onFavoriteRemove)
            }
        }
    }
}

/**
 * Adds a favorite button to the bottom of the screen.
 * @param onFavoriteAdd: function that is called when the favorite button is pressed and location
 * isn't on favorites.
 * @param onFavoriteRemove: function that is called when the favorite button is pressed and location
 * is already in favorites
 */
@Composable
fun Favorite(model: Model, onFavoriteAdd: (String) -> Unit, onFavoriteRemove: (String) -> Unit) {
    val location = model.stats?.first ?: return
    val onClick = {
        if (model.isCountryOnFavorites(location)) {
            onFavoriteRemove(location)
        } else {
            onFavoriteAdd(location)
        }
    }
    ExtendedFloatingActionButton(
        icon = {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite"
            )
        },
        onClick = onClick,
        modifier = Modifier.padding(top = 15.dp),
        text = {
            Text(
                text = if (model.isCountryOnFavorites(location)) "Remove from Favorites" else "Add to Favorites",
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Data(data: CovidStats) {
    val (mainSats, otherStats) = separateStats(data)
    Column() {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(15.dp, 10.dp),
        ) {
            mainSats.forEach { (name, value) ->
                StatLine(name, value)
                Spacer(Modifier.height(15.dp))
            }
        }
        /*
        Box {
            Column() {
                mainSats.forEach { (name, value) ->
                    StatLine(name, value)
                    Spacer(Modifier.height(15.dp))
                }
            }
        }
        Box {
            Column() {
                otherStats.forEach { (name, value) ->
                    StatLine(name, value)
                }
            }
        }

         */
    }
}

/**
 * Displays a single stat line.
 */
@Composable
fun StatLine(str: String, value: Any?) {
    Row {
        val font = FontFamily.Default
        val fontSize = 20.sp
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${str.capitalizeText()} ",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = font,
                style = MaterialTheme.typography.displayMedium,
                //style = TextStyle(textDecoration = TextDecoration.Underline)
            )
            Text(
                text = if (value != null) format(value) else "in fault",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = font,
                style = MaterialTheme.typography.displayMedium,
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