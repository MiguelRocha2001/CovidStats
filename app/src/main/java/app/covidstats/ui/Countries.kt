package app.covidstats.ui

import androidx.compose.foundation.background
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
fun Countries(continent: String?, countries: List<String>?, onContinentClick: (String) -> Unit, onCountryClick: (String) -> Unit) {
    continent?.apply {
        if (countries == null)
            LoadingPage()
        else {
            val continent = this
            Column() {
                Title(title = "Select A Country", textAlign = TextAlign.Center)
                LazyColumn(Modifier.fillMaxWidth()) {
                    item { Option("All Continent", GREEN_WITH_TRANSPARENCY) { onContinentClick(continent) } }
                    countries.forEachIndexed { index, country ->
                        val backgroundColor =
                            if (index % 2 == 0) BLUE_WITH_TRANSPARENCY else RED_WITH_TRANSPARENCY
                        item { Option(country, backgroundColor) { onCountryClick(country) } }
                    }
                }
            }
        }
    }
}

@Composable
private fun Option(text: String, backgroundColor: Color = Color.White, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = null,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(color = backgroundColor)
        ) {
            Text(
                text = text.uppercase(),
                fontSize = 20.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}
