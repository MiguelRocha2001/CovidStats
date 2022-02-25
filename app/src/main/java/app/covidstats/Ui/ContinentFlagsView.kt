package app.covidstats.Ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.covidstats.R
import app.covidstats.model.continents.Continent
import app.covidstats.model.continents.Europe
import app.covidstats.model.continents.NorthAmerica

@Composable
fun ShowContinents(continentHeight: Dp, onClick: (Continent) -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        drawContinent(Europe() ,continentHeight, onClick)
        drawContinent(NorthAmerica() ,continentHeight, onClick)
    }
}

@Composable
private fun drawContinent(continent: Continent, continentHeight: Dp, onClick: (Continent) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(continentHeight / 4)
    ) {
        Button(
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Transparent),
            modifier = Modifier
                .padding(Dp(0f))
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(Dp(0f)),
            onClick = { onClick(continent) }
        ) {
            painterResource(id = continent.imageRes).apply {
                Image(
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(10.dp),
                    painter = this,
                    contentDescription = "$continent Flag",
                )
            }
        }
    }
}