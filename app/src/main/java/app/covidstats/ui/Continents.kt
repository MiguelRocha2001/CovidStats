package app.covidstats.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.data.app.Continent
import app.covidstats.model.data.app.formattedName

@Suppress("NAME_SHADOWING")
@Composable
fun Continents(onClick: (Continent) -> Unit) {
    Column {
        Title(title = "Continents", textAlign = TextAlign.Center)
        Continent.values().forEach { continent ->
            val color = MaterialTheme.colorScheme.secondary
            Continent(continent, onClick, Color.Transparent, color)
        }
    }
}

@Composable
private fun Continent(continent: Continent, onClick: (Continent) -> Unit, backgroundColor: Color, fontColor: Color = Color.White) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(95.dp)
            .fillMaxWidth()
            .background(backgroundColor),
    ) {
        Button(
            elevation = null,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val continentAux = continent
                onClick(continentAux)
            }
        ) {
            Text(
                text = continent.formattedName(),
                color = fontColor,
                fontSize = 23.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
    }
}