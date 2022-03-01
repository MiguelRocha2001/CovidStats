package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val model = remember { initModel(scope) }
    var showMenu by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Screen.ShowWorldCovidStats.route
    ) {
        composable("world_stats") { ShowWorldCovidStats(model) }
        composable("news") { CovidNews(model.news) }
        composable("continent_options") { DisplayContinentOptions() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { showMenu = !showMenu },
            colors = ButtonDefaults.buttonColors(backgroundColor = AMBAR_LIGHT),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (!showMenu)
                Text(text = "Open Menu", color = Color.White, fontSize = 30.sp)
            else
                Text(text = "Close Menu", color = Color.White, fontSize = 30.sp)
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }, modifier = Modifier.fillMaxWidth()) {
            OptionView("World Covid Stats") {
                scope.launch(Dispatchers.IO) { model.loadCovidNews() }
                // closes menu
                showMenu = false
            }
            OptionView("Continent Covid Stats") {
                scope.launch(Dispatchers.IO) {
                    showContinents = model.loadAllContinents()
                    showMenu = false
                }
                model.dumpResults()
            }
            OptionView("Covid News") {
                scope.launch(Dispatchers.IO) {
                    model.loadCovidNews()
                    navController.navigate("news")
                    showMenu = false
                }
                model.dumpResults()
            }
            OptionView("More Info About Covid-19") {
                scope.launch(Dispatchers.IO) {
                    showMenu = false
                }
                model.dumpResults()
            }
            OptionView("Exit") {
                model.dumpResults()
                showMenu = false
                mainActivity.finish()
            }
        }

        val continents = showContinents
        continents?.apply {
            ShowContinents( continents, onClick = { continent ->
                scope.launch(Dispatchers.IO) {
                    showContinents = null
                    showCountries = continent to model.loadAllCountries(continent)
                }}
            )}

        // TODO -> displays country flags
        showCountries?.apply {
            DisplayContinentOptions(
                this.first,
                this.second,
                onContinentClick = { continent ->
                    showCountries = null
                    scope.launch(Dispatchers.IO) { model.loadContinentCovidStats(continent) }
                },
                onCountryClick = { country ->
                    scope.launch(Dispatchers.IO) {
                        showCountries = null
                        model.loadCountryCovidStats(country)
                    }
                }
            )
        }
        if (showNews) {
            CovidNews(model.news)
        }
        ShowWorldCovidStats(model)
    }
}

private fun initModel(scope: CoroutineScope): Model {
    val model = Model()
    scope.launch(Dispatchers.IO) { model.loadWorldCovidStats() }
    return model
}