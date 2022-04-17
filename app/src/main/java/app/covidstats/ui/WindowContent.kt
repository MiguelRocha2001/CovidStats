package app.covidstats.ui

import android.content.Context
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.covidstats.MainActivity
import app.covidstats.model.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainWindow(mainActivity: MainActivity) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val model = remember { initModel(mainActivity, scope) }

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
        composable("favorites") { Favorites(model) { location ->
            scope.launch(Dispatchers.IO) {
                model.loadLocationCovidStats(location)
            }
            navController.navigate("stats")
        }}
        composable("news") { CovidNews(model.news) }
        composable("more_info") { MoreCovidInformation(model.moreCovidInfo) }
        composable("continents") { Continents(model.continents) { continent ->
            scope.launch(Dispatchers.IO) { model.loadAllCountries(continent) }
            navController.navigate("continent_options/${continent}")
        }}
        composable("continent_options/{continent}") { backStackEntry ->
            val continent = backStackEntry.arguments?.getString("continent")
            ContinentOptions(
                continent,
                model.countries?.second ?: emptyList(),
                onContinentClick = { continentOption ->
                    scope.launch(Dispatchers.IO) { model.loadContinentCovidStats(continentOption) }
                    navController.navigate("stats")
                },
                onCountryClick = { country ->
                    scope.launch(Dispatchers.IO) {
                        model.loadLocationCovidStats(country)
                    }
                    navController.navigate("stats")
                }
            )
        }
    }
}

private fun initModel(context: Context, scope: CoroutineScope): Model {
    val model = Model(context, scope)
    scope.launch(Dispatchers.IO) {
        model.loadAllContinents()
    }
    return model
}