package app.covidstats.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.covidstats.MainActivity
import app.covidstats.model.Model
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainPage(model: Model, scope: CoroutineScope, navController: NavHostController, mainActivity: MainActivity) {
    Column {
        Box {
            Menu(model, scope, navController, mainActivity)
        }
        // Spacer(modifier = Modifier.height(15.dp))
        // Favorites(model, onFavoriteClick)
    }
}