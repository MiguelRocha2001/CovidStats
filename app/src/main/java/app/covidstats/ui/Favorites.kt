package app.covidstats.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun Favorites(
    favoriteLocations: List<String>,
    onClick: (String) -> Unit
) {
    if (favoriteLocations.isEmpty())
        Text("No favorites yet", fontSize = 24.sp, textAlign = TextAlign.Center)
    else
        Locations("Favorites", locations = favoriteLocations, onLocationClick = onClick)
}
