package app.covidstats.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
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
        if (favoriteLocations.locations.isEmpty())
            Text(
                "No favorites yet",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        else
            Locations(
                "Favorites",
                locations = favoriteLocations,
                onLocationClick = onClick,
                additionalComposable = additionalComposable
            )
    }
}
