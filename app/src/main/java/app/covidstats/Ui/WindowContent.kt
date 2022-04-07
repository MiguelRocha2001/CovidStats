package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.covidstats.MainActivity
import app.covidstats.model.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainWindow(mainActivity: MainActivity) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val model = remember { initModel(scope) }

    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable("menu") { Menu(model = model, scope = scope, navController = navController, mainActivity) }
        composable("stats") { CovidStats(model) }
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
                onContinentClick = { continent ->
                    scope.launch(Dispatchers.IO) { model.loadContinentCovidStats(continent) }
                    navController.navigate("stats")
                },
                onCountryClick = { country ->
                    scope.launch(Dispatchers.IO) {
                        model.loadCountryCovidStats(country)
                    }
                    navController.navigate("stats")
                }
            )
        }
    }
}

private fun initModel(scope: CoroutineScope): Model {
    val model = Model()
    scope.launch(Dispatchers.IO) {
        model.loadWorldCovidStats()
        model.loadAllContinents()
    }
    return model
}