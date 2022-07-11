package app.covidstats.ui

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
import app.covidstats.ui.theme.Green30
import app.covidstats.ui.theme.Green40
import app.covidstats.ui.theme.Green80
import app.covidstats.ui.theme.GreenGrey50

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("NAME_SHADOWING")
@Composable
fun Continents(onClick: (String) -> Unit) {
    Model.continents.apply {
        val continents = this
        Column {
            Title(title = "Select A Continent", textAlign = TextAlign.Center)
            continents.forEachIndexed { i, continent ->
                val backgroundColor = if (i % 2 == 0) Green40 else Green80
                Continent(continent, onClick, backgroundColor)
            }
        }
    }
}

/*
@Composable
private fun Continent(continent: String, onClick: (String) -> Unit, backgroundColor: Color, fontColor: Color = Color.White) {
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
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Continent(continent: String, onClick: (String) -> Unit, backgroundColor: Color, fontColor: Color = Color.White) {
    ListItem(
        modifier = Modifier.clickable { onClick(continent) },
        headlineText = {
            Text(text = continent)
        }
    )
    Divider()
}