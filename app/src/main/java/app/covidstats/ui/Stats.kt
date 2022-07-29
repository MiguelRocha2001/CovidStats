package app.covidstats.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.data.app.Stats
import app.covidstats.model.data.app.StatsError
import app.covidstats.model.data.app.StatsSuccess
import app.covidstats.model.data.app.formattedName
import app.covidstats.model.opers.Model
import app.covidstats.model.data.covid_stats.CovidStats
import app.covidstats.ui.commons.OnServerError
import kotlin.reflect.full.memberProperties

@Composable
fun Stats(model: Model, onFavoriteAdd: (String) -> Unit, onFavoriteRemove: (String) -> Unit) {
    val configuration = LocalConfiguration.current
    val stats = model.stats
    if (stats != null) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                StatsLandscape(configuration, model, onFavoriteAdd, onFavoriteRemove)
            }
            else -> {
                StatsPortrait(configuration, model, onFavoriteAdd, onFavoriteRemove)
            }
        }
    }
}

@Composable
private fun StatsPortrait(
    configuration: Configuration,
    model: Model,
    onFavoriteAdd: (String) -> Unit,
    onFavoriteRemove: (String) -> Unit
) {
    val stats = model.stats ?: throw IllegalStateException("Stats is null")
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
        ) {
            Title(title = "${stats.name.formattedName()} Covid-19 Stats")
            ListStats(configuration, model, onFavoriteAdd, onFavoriteRemove)
        }
    } else {
        throw IllegalStateException("Orientation is not portrait")
    }
}

@Composable
private fun StatsLandscape(
    configuration: Configuration,
    model: Model,
    onFavoriteAdd: (String) -> Unit,
    onFavoriteRemove: (String) -> Unit
) {
    val stats = model.stats ?: throw IllegalStateException("Stats is null")
    Row {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxHeight(0.66f).padding(end = 10.dp)
            ) {
                Title("${stats.name.formattedName()} Covid-19 Stats")
            }
            Favorite(model, onFavoriteAdd, onFavoriteRemove)
        }
        Box(
            modifier = Modifier
                .weight(2f)
        ) {
            ListStats(configuration, model, onFavoriteAdd, onFavoriteRemove)
        }
    }
}

@Composable
private fun ListStats(configuration: Configuration, model: Model, onFavoriteAdd: (String) -> Unit, onFavoriteRemove: (String) -> Unit) {
    when (val covidStats = model.stats) {
        is StatsSuccess -> {
            covidStats.data.apply {
                Data(configuration, data = this)
            }
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Favorite(model, onFavoriteAdd, onFavoriteRemove)
            }
        }
        is StatsError -> {
            OnServerError()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Data(configuration: Configuration, data: CovidStats) {
    val (mainSats, otherStats) = separateStats(data)
    val modifier = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        Modifier.fillMaxWidth()
    else
        Modifier
    Card(
        modifier = modifier
            .fillMaxHeight(0.85f),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
           dataItem(configuration, mainSats, otherStats)
        }
    }
}

private fun LazyListScope.dataItem(
    configuration: Configuration,
    mainStats:  List<Pair<String, Any?>>,
    otherStats:  List<Pair<String, Any?>>
) {
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        dataItemPortrait(mainStats, otherStats)
    } else {
        dataItemLandscape(mainStats, otherStats)
    }

}

private fun LazyListScope.dataItemLandscape(
    mainStats: List<Pair<String, Any?>>,
    otherStats: List<Pair<String, Any?>>
) {
    repeat(mainStats.size + otherStats.size) { i ->
        if (i % 2 == 0) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (i < mainStats.size) {
                        Box(modifier = Modifier.weight(1f)) {
                            StatLine(mainStats[i / 2].first, mainStats[i / 2].second)
                        }
                    }
                    if (i < otherStats.size) {
                        Box(modifier = Modifier.weight(1f)) {
                            StatLine(otherStats[i / 2].first, otherStats[i / 2].second)
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.dataItemPortrait(
    mainStats: List<Pair<String, Any?>>,
    otherStats: List<Pair<String, Any?>>
) {
    mainStats.forEach { (name, value) ->
        item { StatLine(name, value) }
        item { Spacer(Modifier.height(15.dp)) }
    }
    item { Divider(modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)) }
    otherStats.forEach { (name, value) ->
        item { StatLine(name, value) }
    }
}

/**
 * Displays a single stat line.
 */
@Composable
fun StatLine(str: String, value: Any?) {
    Column {
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