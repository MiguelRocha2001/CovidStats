package app.covidstats.Ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.covidstats.R
import app.covidstats.model.Model

enum class Continents(val str: String) {
    EUROPE("europe"), AFRICA("africa"), ASIA("asia"), OCEANIA("oceania"), NORTH_AMERICA("north america"), SOUTH_AMERICA("south america")
}

@Composable
fun ShowContinents(continentHeight: Dp, onClick: (String) -> Unit) {
    Column(Modifier.fillMaxHeight()) {
        Continents.values().forEach { continent ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(continentHeight / 6)
            ) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Transparent),
                    modifier = Modifier
                        .padding(Dp(0f))
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(Dp(0f)),
                    onClick = { onClick(continent.str) }
                ) {
                    val image = when (continent) {
                        Continents.EUROPE -> painterResource(id = R.drawable.europe)
                        Continents.AFRICA -> painterResource(id = R.drawable.africa)
                        Continents.ASIA -> painterResource(id = R.drawable.asia)
                        Continents.OCEANIA -> painterResource(id = R.drawable.oceania)
                        Continents.NORTH_AMERICA -> painterResource(id = R.drawable.north_america)
                        Continents.SOUTH_AMERICA -> painterResource(id = R.drawable.south_america)
                    }
                    Image(
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(10.dp),
                        painter = image,
                        contentDescription = "$continent Union Flag",
                    )
                }
            }
        }
    }
}