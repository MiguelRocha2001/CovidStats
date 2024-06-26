package app.covidstats.ui.navigation

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import app.covidstats.model.data.app.LocationsSuccess
import app.covidstats.model.data.app.formattedName
import app.covidstats.model.data.app.toContinent
import app.covidstats.model.opers.Model
import app.covidstats.ui.Locations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CountriesView(
    model: Model,
    searchHandler: ((() -> Unit)?) -> Unit,
    backStackEntry: NavBackStackEntry,
    navController: NavHostController,
    scope: CoroutineScope
) {
    var localSearchHandler by remember { mutableStateOf<( @Composable () -> Unit)?>(null) }
    searchHandler {
        localSearchHandler = getSearchTextField { location ->
            scope.launch(Dispatchers.IO) {
                model.filterLocations(location)
            }
        }
    }

    val continent = backStackEntry.arguments?.getString("continent")?.formattedName() ?: ""
    Locations(
        title = "Countries in $continent",
        standardLocations = model.countries?.second?.let {
            it to { country ->
                scope.launch(Dispatchers.IO) {
                    model.loadLocationCovidStats(country)
                }
                navController.navigate("stats")
            }
        },
        specialLocations = LocationsSuccess(listOf(continent)) to { continentOption ->
            scope.launch(Dispatchers.IO) {
                val continent = continentOption.toContinent()
                    ?: throw IllegalStateException("Continent not found")
                model.loadContinentCovidStats(continent)
            }
            navController.navigate("stats")
        },
        additionalComposable = localSearchHandler
    )
}