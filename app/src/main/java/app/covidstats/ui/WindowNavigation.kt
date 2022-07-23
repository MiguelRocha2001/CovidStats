package app.covidstats.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.covidstats.model.opers.Model
import app.covidstats.ui.views.*
import kotlinx.coroutines.CoroutineScope

@Composable
fun windowNavigation(
    navController: NavHostController,
    scope: CoroutineScope,
    model: Model,
    searchHandler: ((() -> Unit)?) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Continents.route
    ) {
        composable("stats") {
            StatsView(searchHandler, model)
        }
        composable("favorites") {
            FavoritesView(searchHandler, model, scope, navController)
        }
        composable("more_info") {
            MoreInfoView(searchHandler, model)
        }
        composable("continents") {
            ContinentsView(searchHandler, model, navController, scope)
        }
        composable("continent_options/{continent}") { backStackEntry ->
            CountriesView(model, searchHandler, backStackEntry, navController, scope)
        }
    }
}