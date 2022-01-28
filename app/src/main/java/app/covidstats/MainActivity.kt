package app.covidstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import app.covidstats.Ui.ShowWorldCovidStats
import app.covidstats.uris.Model

// TODO make composable to format covid results
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val covidStats = remember { Model() }
            var showWorldCovidStats by remember { mutableStateOf(false)}
            var showMenu by remember { mutableStateOf(false)}

            Button(
                onClick = { showMenu = !showMenu },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dp(70f))
            ) {
                if (!showMenu)
                    Text(text = "Open Menu")
                else
                    Text(text = "Close Menu")
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = { showWorldCovidStats = !showWorldCovidStats }) {
                    Text(text = "Show World Covid Stats")
                }
                DropdownMenuItem(onClick = { showWorldCovidStats = !showWorldCovidStats }) {
                    Text(text = "Show World Covid Stats")
                }
            }
            Column() {
                if (showWorldCovidStats) {
                    covidStats.getCovidCases()
                    ShowWorldCovidStats(covidStats.results)
                }
            }
        }
    }
}

