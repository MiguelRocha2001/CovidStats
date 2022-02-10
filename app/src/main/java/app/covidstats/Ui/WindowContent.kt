package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.Model

@Composable
fun MainWindow() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val menuHeight = screenHeight / 10
    val continentHeight = screenHeight - menuHeight

    val model = remember { Model() }
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
                model.getWorldCovidStats()
                // closes menu
                showMenu = false
                showContinentFlags = false
            }
            OptionView("Show Continent Covid Stats") {
                model.emptyResults()
                showContinentFlags = true
                showMenu = false
            }
            OptionView("Exit") {
                model.emptyResults()
                showMenu = false
            }
        }
        if (showContinentFlags) {
            ShowContinents(continentHeight, onClick = { continent ->
                showContinentFlags = false
                model.getContinentCovidStats(continent)
            })
        }
        ShowWorldCovidStats(model)
    }
}