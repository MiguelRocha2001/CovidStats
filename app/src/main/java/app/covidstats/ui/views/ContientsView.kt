package app.covidstats.ui.views

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.covidstats.model.opers.Model
import app.covidstats.ui.Continents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ContinentsView(
    searchHandler: ((() -> Unit)?) -> Unit,
    model: Model,
    navController: NavHostController,
    scope: CoroutineScope
) {
    searchHandler(null)
    Continents { continent ->
        Log.i("WindowNavigation", "Composing Continents view")
        model.dumpCountries()
        scope.launch(Dispatchers.IO) {
            Thread.sleep(200)
            model.loadContinentCountries(continent)
        }
        navController.navigate("continent_options/${continent}")
    }
}