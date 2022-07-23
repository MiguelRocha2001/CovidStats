package app.covidstats.ui.views

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import app.covidstats.model.opers.Model
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
    var localSearchHandler by remember { mutableStateOf<( @Composable () -> Unit)?>(null) }
    searchHandler {
        localSearchHandler = getSearchTextField { newLocation ->
            scope.launch(Dispatchers.IO) {
                model.filterFavorites(newLocation)
            }
        }
    }

    Favorites(
        model.favoriteLocations,
        localSearchHandler
    ) { location ->
        scope.launch(Dispatchers.IO) {
            model.loadLocationCovidStats(location)
        }
        navController.navigate("stats")
    }
}