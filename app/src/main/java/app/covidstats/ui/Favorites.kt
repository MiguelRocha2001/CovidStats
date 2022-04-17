package app.covidstats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.Model

@Composable
fun Favorites(model: Model, onClick: (String) -> Unit) {
    val textModifier = Modifier.padding(16.dp)
    Column(modifier = textModifier) {
        Title(title = "Favorites", textAlign = TextAlign.Center)
        if (model.favoriteLocations.isEmpty()) {
            Text("No favorites yet", fontSize = 24.sp, textAlign = TextAlign.Center)
        } else {
            model.favoriteLocations.forEach {
                Button(
                    modifier = Modifier.padding(bottom = 10.dp),
                    onClick = { onClick(it) }
                ) {
                    Text(it, fontSize = 24.sp)
                }
            }
        }
    }
}

