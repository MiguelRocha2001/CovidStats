package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.continents.Continent

@Composable
fun DisplayContinentOptions(continent: Continent, onCountryClick: (String) -> Unit, onContinentClick: (String) -> Unit) {
    DisplayOption("All Continent") { onContinentClick(continent.string) }
    LazyColumn(Modifier.fillMaxWidth()) {
        continent.countries.forEach { country ->
            item {
                DisplayOption(text = country.string.uppercase()) { onCountryClick(country.string) }
            }
        }
    }
}

@Composable
private fun DisplayOption(text: String, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Text(
                //text = country.string.replaceFirstChar { it.uppercaseChar() },
                text = text.uppercase(),
                fontSize = 20.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}
