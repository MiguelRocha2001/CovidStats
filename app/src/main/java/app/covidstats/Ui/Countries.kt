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

@Composable
fun DisplayContinentOptions(continent: String, countries: List<String>, onContinentClick: (String) -> Unit, onCountryClick: (String) -> Unit) {
    DisplayOption("All Continent") { onContinentClick(continent) }
    LazyColumn(Modifier.fillMaxWidth()) {
        countries.forEach { country ->
            item {
                DisplayOption(text = country.uppercase()) { onCountryClick(country) }
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
