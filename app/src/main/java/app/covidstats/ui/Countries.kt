package app.covidstats.ui

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
fun ContinentOptions(continent: String?, countries: List<String>?, onContinentClick: (String) -> Unit, onCountryClick: (String) -> Unit) {
    continent?.apply {
        if (countries == null)
            LoadingPage()
        else {
            val continent = this
            LazyColumn(Modifier.fillMaxWidth()) {
                item { Option("All Continent") { onContinentClick(continent) } }
                countries.forEach { country ->
                    item { Option(country) { onCountryClick(country) } }
                }
            }
        }
    }
}

@Composable
private fun Option(text: String, onClick: () -> Unit) {
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
