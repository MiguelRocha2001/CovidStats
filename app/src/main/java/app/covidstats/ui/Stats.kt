package app.covidstats.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.data.app.StatsError
import app.covidstats.model.data.app.StatsLoading
import app.covidstats.model.data.app.StatsSuccess
import app.covidstats.model.opers.Model
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.ui.commons.OnServerError
import kotlin.reflect.full.memberProperties

@Composable
fun Stats(model: Model?, onFavoriteAdd: (String) -> Unit, onFavoriteRemove: (String) -> Unit) {
    if (model != null) {
        val covidStats = model.stats
        if (covidStats != null) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
            ) {
                Title(title = "${covidStats.name.capitalizeText()} Covid-19 Stats")
                when (covidStats) {
                    is StatsSuccess -> {
                        // depending on what type of data is to be displayed
                        covidStats.data.apply {
                            Data(data = this)
                        }
                        Favorite(model, onFavoriteAdd, onFavoriteRemove)
                    }
                    is StatsError -> {
                        OnServerError()
                    }
                }
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
    val location = model.stats?.name ?: return
    val onClick = {
        if (model.isCountryOnFavorites(location)) {
            onFavoriteRemove(location)
        } else {
            onFavoriteAdd(location)
        }
    }
    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Data(data: CovidStats) {
    val (mainSats, otherStats) = separateStats(data)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            mainSats.forEach { (name, value) ->
                item { StatLine(name, value) }
                item { Spacer(Modifier.height(15.dp)) }
            }
            item { Divider(modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)) }
            otherStats.forEach { (name, value) ->
                item { StatLine(name, value) }
            }
        }
    }
}

/**
 * Displays a single stat line.
 */
@Composable
fun StatLine(str: String, value: Any?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val font = FontFamily.Default
        val fontSize = 20.sp
        Text(
            text = str.capitalizeText(),
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.secondary,
            fontFamily = font,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Spacer(modifier = Modifier.width(15.dp))
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