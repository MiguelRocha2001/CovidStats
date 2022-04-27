package app.covidstats.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.Model

@Suppress("NAME_SHADOWING")
@Composable
fun Continents(onClick: (String) -> Unit) {
    Model.continents.apply {
        val continents = this
        Column {
            Title(title = "Select A Continent", textAlign = TextAlign.Center)
            continents.forEachIndexed { i, continent ->
                val backgroundColor = if (i % 2 == 0) BLUE_WITH_TRANSPARENCY else RED_WITH_TRANSPARENCY
                Continent(continent, onClick, backgroundColor)
            }
        }
    }
}

@Composable
private fun Continent(continent: String, onClick: (String) -> Unit, backgroundColor: Color, fontColor: Color = Color.White) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(95.dp)
            .fillMaxWidth()
            .background(backgroundColor),
    ) {
        Button(
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClick(continent) }
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