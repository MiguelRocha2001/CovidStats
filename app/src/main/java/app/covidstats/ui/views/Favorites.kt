package app.covidstats.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.covidstats.model.Model
import app.covidstats.ui.Favorites
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FavoritesView(
    searchHandler: ((() -> Unit)?) -> Unit,
    model: Model,
    scope: CoroutineScope,
    navController: NavHostController
) {
    searchHandler(null)
    Favorites(model.favoriteLocations) { location ->
        scope.launch(Dispatchers.IO) {
            model.loadLocationCovidStats(location)
        }
        navController.navigate("stats")
    }
}