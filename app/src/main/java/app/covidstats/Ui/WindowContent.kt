package app.covidstats.Ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import app.covidstats.R
import app.covidstats.model.Model

@Composable
fun MainWindow() {
    val covidStats = remember { Model() }
    var showMenu by remember { mutableStateOf(false) }
    var showContinentFlags by remember { mutableStateOf(false) }
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
                covidStats.emptyResults()
                showContinentFlags = true
                showMenu = false
            }
            OptionView("Exit") {
                covidStats.emptyResults()
                showMenu = false
            }
        }
        if (showContinentFlags) {
            Button(onClick = {
                showContinentFlags = false
                covidStats.getContinentCovidStats("Europe")
            }) {
                val image: Painter = painterResource(id = R.drawable.european_union_flag)
                Row(Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier.weight(1f, fill = false)
                            .aspectRatio(image.intrinsicSize.width / image.intrinsicSize.height)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Fit,
                        painter = image,
                        contentDescription = "European Union Flag",
                    )
                }
            }
        }
        ShowWorldCovidStats(covidStats.results)
    }
}