package app.covidstats.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.covidstats.MainActivity
import app.covidstats.model.Model
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(model: Model, scope: CoroutineScope, navController: NavHostController, mainActivity: MainActivity) {
    Box {
        //Menu(model, scope, navController, mainActivity)
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