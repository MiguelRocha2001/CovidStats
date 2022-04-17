package app.covidstats.Ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            scope.launch(Dispatchers.IO) {
                model.loadAllContinents()
            }
            navController.navigate("continents")
            model.dumpResults()
        }
        OptionView("Covid News") {
            scope.launch(Dispatchers.IO) {
                model.loadCovidNews()
            }
            navController.navigate("news")
            model.dumpResults()
        }
        OptionView("More Info About Covid-19") {
            navController.navigate("more_info")
            model.dumpResults()
        }
        OptionView("Exit") {
            model.dumpResults()
            mainActivity.finish()
        }
    }
}