package app.covidstats.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import app.covidstats.MainActivity
import app.covidstats.model.opers.Model
import app.covidstats.model.data.app.Option
import com.plcoding.material3app.ui.theme.Material3AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainWindow(mainActivity: MainActivity, model: Model) {
    val scope = rememberCoroutineScope()
    val model = remember { model }
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    var searchHandler by remember { mutableStateOf<(() -> Unit)?>(null) }

    Material3AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        { toggleMenu(drawerState, scope) },
                        searchAction = searchHandler
                    )
                }
            ) { values ->
                Column(Modifier.padding(values)) {
                    NavigationMenu(
                        selected = Option.CONTINENT,
                        drawerState,
                        onOptionClick = {
                            scope.launch { drawerState.close() }
                            executeOption(it, model, scope, navController, mainActivity)
                        }
                    ) {
                        windowNavigation(navController, scope, model) { handler ->
                            searchHandler = handler
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun toggleMenu(drawerState: DrawerState, scope: CoroutineScope) {
    if (drawerState.isClosed) scope.launch { drawerState.open() }
    else scope.launch { drawerState.close() }
}