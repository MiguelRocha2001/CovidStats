package app.covidstats.Ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.Model

@Composable
fun MainWindow() {
    val covidStats = remember { Model() }
    var showMenu by remember { mutableStateOf(false) }
    var option by remember { mutableStateOf<(() -> Unit)?>(null) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { showMenu = !showMenu },
            colors = ButtonDefaults.buttonColors(backgroundColor = AMBAR_LIGHT),
            modifier = Modifier
                .fillMaxWidth()
                .height(Dp(70f))
        ) {
            if (!showMenu)
                Text(text = "Open Menu", color = Color.White, fontSize = 30.sp)
            else
                Text(text = "Close Menu", color = Color.White, fontSize = 30.sp)
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }, modifier = Modifier.fillMaxWidth()) {
            OptionView("Show World Covid Stats") {
                covidStats.getWorldCovidStats()
                // closes menu
                showMenu = false
            }
            OptionView("Show Continent Covid Stats") {
                covidStats.getContinentCovidStats("Europe")
                showMenu = false
            }
            OptionView("Exit") {
                covidStats.emptyResults()
                showMenu = false
            }
        }
        ShowWorldCovidStats(covidStats.results)
    }
}