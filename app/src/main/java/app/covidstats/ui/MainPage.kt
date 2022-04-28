package app.covidstats.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
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
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = BLUE,
                contentColor = Color.White,
            ) { /* Top app bar content */ }
        },
        drawerContent = {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            // Drawer items
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = BLUE,
                contentColor = Color.White,
            ) { /* Bottom app bar content */ }
        }
    ) {
        Box {
            Menu(model, scope, navController, mainActivity)
        }
    }
    /*
    Column {
        Box {
            Menu(model, scope, navController, mainActivity)
        }
        // Spacer(modifier = Modifier.height(15.dp))
        // Favorites(model, onFavoriteClick)
    }
     */
}