package app.covidstats.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import app.covidstats.model.data.app.Continent
import app.covidstats.model.data.app.formattedName

@Composable
fun Continents(onClick: (Continent) -> Unit) {
    val configuration = LocalConfiguration.current
    val fontColor = MaterialTheme.colorScheme.secondary
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            ContinentsLandscape(configuration, fontColor, onClick)
        } else -> {
            ContinentsPortrait(configuration, fontColor, onClick)
        }
    }
}

@Composable
private fun ContinentsLandscape(configuration: Configuration, fontColor: Color, onClick: (Continent) -> Unit) {
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Title("Continents")
            }
            Box(modifier = Modifier.weight(2f)) {
                ContinentsListing(configuration, fontColor, onClick)
            }
        }
    }
}

@Composable
private fun ContinentsPortrait(configuration: Configuration, fontColor: Color, onClick: (Continent) -> Unit) {
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column {
            Title("Continents")
            ContinentsListing(configuration, fontColor, onClick)
        }
    }
}

@Composable
private fun ContinentsListing(configuration: Configuration, fontColor: Color, onClick: (Continent) -> Unit) {
    LazyColumn(
        content = {
            val continents = Continent.values()
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    continentsLandscape(continents, fontColor, onClick)
                }
                else -> {
                    continentsPortrait(continents, fontColor, onClick)
                }
            }
        }
    )
}

private fun LazyListScope.continentsLandscape(continents: Array<Continent>, fontColor: Color, onClick: (Continent) -> Unit) {
    continents.forEachIndexed { index, continent ->
        if (index % 2 == 0) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Continent(continent, fontColor, onClick)
                    if (index + 1 < continents.size) {
                        Continent(continents[index + 1], fontColor, onClick)
                    }
                }
            }
        }
    }
}

private fun LazyListScope.continentsPortrait(continents: Array<Continent>, fontColor: Color, onClick: (Continent) -> Unit) {
    continents.forEach { continent ->
        item { Continent(continent, fontColor, onClick) }
    }
}

@Composable
private fun Continent(
    continent: Continent,
    fontColor: Color = Color.White,
    onClick: (Continent) -> Unit
) {
    Button(
        elevation = null,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            val continentAux = continent
            onClick(continentAux)
        }
    ) {
        Text(
            text = continent.formattedName(),
            color = fontColor,
            fontSize = 6.em,
            modifier = Modifier.padding(vertical = 20.dp)
        )
    }
}