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

@Suppress("NAME_SHADOWING")
@Composable
fun Continents(continents: List<String>?, onClick: (String) -> Unit) {
    continents?.apply {
        val continents = this
        Column {
            Title(title = "Select A Continent", textAlign = TextAlign.Center)
            continents.forEachIndexed { i, continent ->
                val backgroundColor = if (i % 2 == 0) BLUE_WITH_TRANSPARENCY else RED_WITH_TRANSPARENCY
                val fontColor = Color.White
                Continent(continent, onClick, backgroundColor, fontColor)
            }
        }
    }
}

@Composable
private fun Continent(continent: String, onClick: (String) -> Unit, backgroundColor: Color, fontColor: Color = Color.Black) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(100.dp).background(backgroundColor)
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