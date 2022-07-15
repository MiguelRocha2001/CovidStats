package app.covidstats.ui

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.Model
import app.covidstats.model.data.other.Continent
import app.covidstats.model.data.other.toContinent

@Suppress("NAME_SHADOWING")
@Composable
fun Continents(onClick: (Continent) -> Unit) {
    Model.continents.apply {
        val continents = this
        Column {
            Title(title = "Select A Continent", textAlign = TextAlign.Center)
            continents.forEach { continent ->
                val color = MaterialTheme.colorScheme.secondary
                Continent(continent, onClick, Color.Transparent, color)
            }
        }
    }
}

@Composable
private fun Continent(continent: String, onClick: (Continent) -> Unit, backgroundColor: Color, fontColor: Color = Color.White) {
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
                val continentAux = continent.toContinent()
                    ?: throw IllegalArgumentException("Invalid continent").also { Log.i("ContinentView", "Selected continent is invalid.") }
                onClick(continentAux)
            }
        ) {
            Text(
                text = continent,
                color = fontColor,
                fontSize = 23.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
    }
}