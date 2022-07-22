package app.covidstats.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.covidstats.MainActivity
import app.covidstats.model.Model
import app.covidstats.model.data.other.formattedName
import app.covidstats.model.data.other.toContinent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun windowNavigation(
    navController: NavHostController,
    scope: CoroutineScope,
    model: Model
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Continents.route
    ) {
        composable("stats") {
            CovidStats(
                model,
                onFavoriteAdd = { model.addFavoriteLocation(it) },
                onFavoriteRemove = {model.removeFavoriteLocation(it) }
            )
        }
        composable("favorites") { Favorites(model.favoriteLocations) { location ->
            scope.launch(Dispatchers.IO) {
                model.loadLocationCovidStats(location)
            }
            navController.navigate("stats")
        }}
        composable("more_info") { MoreCovidInformation(model.moreCovidInfo) }
        composable("continents") { Continents { continent ->
            Log.i("WindowNavigation", "Composing Continents view")
            model.dumpCountries()
            navController.navigate("continent_options/${continent}")
            scope.launch(Dispatchers.IO) {
                Thread.sleep(200)
                model.loadContinentCountries(continent)
            }
        }}
        composable("continent_options/{continent}") { backStackEntry ->
            model.dumpStats()
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
                additional = arrayOf ( continent to
                        { continentOption ->
                            navController.navigate("stats")
                            scope.launch(Dispatchers.IO) {
                                val continent = continentOption.toContinent() ?: throw IllegalStateException("Continent not found")
                                model.loadContinentCovidStats(continent)
                            }
                        }
                )
            )
        }
    }
}