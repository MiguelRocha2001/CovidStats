package app.covidstats.Ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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