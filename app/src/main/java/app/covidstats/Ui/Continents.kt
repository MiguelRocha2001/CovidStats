package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowContinents(continents: List<String>, onClick: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        continents.forEach{continent ->
            DrawContinent(continent, onClick)
        }
    }
}

@Composable
private fun DrawContinent(continent: String, onClick: (String) -> Unit) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        Button(
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.Transparent),
            modifier = Modifier
                .padding(Dp(0f))
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(Dp(0f)),
            onClick = { onClick(continent) }
        ) {
            Text(
            text = continent,
            fontSize = 40.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
    }
}