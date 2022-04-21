package app.covidstats.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import app.covidstats.MainActivity
import app.covidstats.model.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Menu(model: Model, scope: CoroutineScope, navController: NavHostController, mainActivity: MainActivity) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = {  },
        modifier = Modifier.fillMaxWidth()
    ) {
        OptionView("Favorites") {
            navController.navigate("favorites")
        }
        OptionView("World Covid Stats") {
            scope.launch(Dispatchers.IO) { model.loadWorldCovidStats() }
            navController.navigate("stats")
        }
        OptionView("Continent Covid Stats") {
            navController.navigate("continents")
            model.dumpStats()
        }
        OptionView("Covid News") {
            scope.launch(Dispatchers.IO) {
                model.loadCovidNews()
            }
            navController.navigate("news")
            model.dumpStats()
        }
        OptionView("More Info About Covid-19") {
            navController.navigate("more_info")
            model.dumpStats()
        }
        OptionView("Exit") {
            model.dumpStats()
            mainActivity.finish()
        }
    }
}