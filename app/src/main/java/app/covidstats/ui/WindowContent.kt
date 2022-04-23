package app.covidstats.ui

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.covidstats.MainActivity
import app.covidstats.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainWindow(mainActivity: MainActivity) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val model = remember { Model(mainActivity, scope) }

    NavHost(
        navController = navController,
        startDestination = Screen.MainPage.route
    ) {
        composable("main_page") {
            MainPage(
                model = model,
                scope = scope,
                navController = navController,
                mainActivity
            )
        }
        composable("stats") {
            CovidStats(
                model,
                onFavoriteAdd = {model.addFavoriteLocation(it) },
                onFavoriteRemove = {model.removeFavoriteLocation(it) }
            )
        }
        composable("favorites") { Favorites(model.favoriteLocations) { location ->
            scope.launch(Dispatchers.IO) {
                model.loadLocationCovidStats(location)
            }
            navController.navigate("stats")
        }}
        composable("news") { CovidNews(model.news) }
        composable("wait") { LoadingPage() }
        composable("more_info") { MoreCovidInformation(model.moreCovidInfo) }
        composable("continents") { Continents(model.continents) { continent ->
            model.dumpCountries()
            navController.navigate("continent_options/${continent}")
            scope.launch(Dispatchers.IO) {
                Thread.sleep(200)
                model.loadContinentCountries(continent)
            }
        }}
        composable("continent_options/{continent}") { backStackEntry ->
            model.dumpStats()
            val continent = backStackEntry.arguments?.getString("continent") ?: ""
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
                            Thread.sleep(200)
                            model.loadContinentCovidStats(continentOption)
                        }
                    }
                )
            )
        }
    }
}