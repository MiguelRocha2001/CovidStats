package app.covidstats.ui.views

import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import app.covidstats.model.Model
import app.covidstats.model.data.other.formattedName
import app.covidstats.model.data.other.toContinent
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
    model.dumpStats()

    var localSearchHandler by remember { mutableStateOf<( @Composable () -> Unit)?>(null) }
    searchHandler {
        localSearchHandler = localSearchHandler(scope, model)
    }

    val continent = backStackEntry.arguments?.getString("continent")?.formattedName() ?: ""
    Locations(
        locations = model.countries?.second,
        onLocationClick = { country ->
            navController.navigate("stats")
            scope.launch(Dispatchers.IO) {
                Thread.sleep(200)
                model.loadLocationCovidStats(country)
            }
        },
        additionalLocations = arrayOf(continent to
                { continentOption ->
                    navController.navigate("stats")
                    scope.launch(Dispatchers.IO) {
                        val continent = continentOption.toContinent()
                            ?: throw IllegalStateException("Continent not found")
                        model.loadContinentCovidStats(continent)
                    }
                }
        ),
        additionalComposable = localSearchHandler
    )
}

private fun localSearchHandler(scope: CoroutineScope, model: Model): @Composable () -> Unit = {
    var text by remember { mutableStateOf("") }
    TextField(value = text, onValueChange = ({ location ->
        text = location
        scope.launch(Dispatchers.IO) {
            model.filterLocations(location)
        }
    }))
}