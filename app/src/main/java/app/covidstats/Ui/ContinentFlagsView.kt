package app.covidstats.Ui

import androidx.compose.foundation.*
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
import app.covidstats.model.Continent
import app.covidstats.model.Model

@Composable
fun ShowContinents(continentHeight: Dp, onClick: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Continent.values().forEach { continent ->
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
                    onClick = { onClick(continent.str) }
                ) {
                    val image = when (continent) {
                        Continent.EUROPE -> painterResource(id = R.drawable.europe)
                        Continent.AFRICA -> painterResource(id = R.drawable.africa)
                        Continent.ASIA -> painterResource(id = R.drawable.asia)
                        Continent.OCEANIA -> painterResource(id = R.drawable.oceania)
                        Continent.NORTH_AMERICA -> painterResource(id = R.drawable.north_america)
                        Continent.SOUTH_AMERICA -> painterResource(id = R.drawable.south_america)
                    }
                    Image(
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(10.dp),
                        painter = image,
                        contentDescription = "$continent Flag",
                    )
                }
            }
        }
    }
}