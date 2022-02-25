package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.MainActivity
import app.covidstats.model.continents.Continent
import app.covidstats.model.Model

@Composable
fun MainWindow(mainActivity: MainActivity) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val menuHeight = screenHeight / 10
    val continentHeight = screenHeight - menuHeight

    val model = remember { initModel() }
    var showMenu by remember { mutableStateOf(false) }
    var showContinentFlags by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { showMenu = !showMenu },
            colors = ButtonDefaults.buttonColors(backgroundColor = AMBAR_LIGHT),
            modifier = Modifier
                .fillMaxWidth()
                .height(menuHeight)
        ) {
            if (!showMenu)
                Text(text = "Open Menu", color = Color.White, fontSize = 30.sp)
            else
                Text(text = "Close Menu", color = Color.White, fontSize = 30.sp)
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }, modifier = Modifier.fillMaxWidth()) {
            OptionView("Show World Covid Stats") {
                model.loadWorldCovidStats()
                // closes menu
                showMenu = false
                showContinentFlags = false
            }
            OptionView("Show Continent Covid Stats") {
                model.dumpResults()
                showContinentFlags = true
                showMenu = false
            }
            OptionView("Exit") {
                model.dumpResults()
                showContinentFlags = false
                showMenu = false
                mainActivity.finish()
            }
        }
        if (showContinentFlags) {
            ShowContinents(continentHeight, onClick = { continent ->
                showContinentFlags = false
                displayCountries(continent)
                // TODO -> Show option for display all stats or a specific country
                model.loadContinentCovidStats(continent)
            })
        }
        ShowWorldCovidStats(model)
    }
}

fun displayCountries(continent: Continent) {
    LazyColumn(Modifier.fillMaxWidth()) {
        continent.Country.values()
    }
}

private fun initModel(): Model {
    val model = Model()
    model.loadWorldCovidStats()
    return model
}