package app.covidstats.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.data.app.Locations
import app.covidstats.model.data.app.LocationsSuccess

@Composable
fun Favorites(
    favoriteLocations: Locations,
    additionalComposable: @Composable (() -> Unit)? = null,
    onClick: (String) -> Unit
) {
    if (favoriteLocations is LocationsSuccess) {
        if (favoriteLocations.locations.isEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "No favorites yet",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        else
            Locations(
                title = "Favorites",
                standardLocations = favoriteLocations to onClick,
                specialLocations = null,
                additionalComposable
            )
    }
}
