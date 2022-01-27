package app.covidstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import app.covidstats.Ui.ShowWorldCovidStats
import app.covidstats.uris.Model

// TODO make composable to format covid results
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val covidStats by remember { mutableStateOf(Model()) }

            Column() {
                Button(onClick = { covidStats.getCovidCases() }) {
                    Text(text = "Show Results", fontSize = 60.sp)
                }
                ShowWorldCovidStats(covidStats.results)
            }
        }
    }
}

