package app.covidstats.ui

import android.util.Log
import androidx.navigation.NavHostController
import app.covidstats.MainActivity
import app.covidstats.model.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun executeOption(option: Option, model: Model, scope: CoroutineScope, navController: NavHostController, mainActivity: MainActivity) {
    when (option) {
        Option.HOME -> {
            Log.i("Navigation", "Navigating to Home")
        }
        Option.FAVORITES -> {
            Log.i("Navigation", "Navigating to Favorites")
            navController.navigate("favorites")
        }
        Option.WORLD -> {
            Log.i("Navigation", "Navigating to World")
            scope.launch(Dispatchers.IO) { model.loadWorldCovidStats() }
            navController.navigate("stats")
        }
        Option.CONTINENT -> {
            Log.i("Navigation", "Navigating to Continents")
            navController.navigate("continents")
            model.dumpStats()
        }
        Option.NEWS -> {
            Log.i("Navigation", "Navigating to News")
            scope.launch(Dispatchers.IO) {
                model.loadCovidNews()
            }
            navController.navigate("news")
            model.dumpStats()
        }
        Option.INFO -> {
            Log.i("Navigation", "Navigating to Info")
            navController.navigate("more_info")
            model.dumpStats()
        }
        Option.EXIT -> {
            Log.i("Navigation", "Exiting")
            model.dumpStats()
            mainActivity.finish()
        }
    }
}