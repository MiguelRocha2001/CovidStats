package app.covidstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import app.covidstats.ui.Model

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val results by remember { mutableStateOf(Model()) }
            Column() {
                Button(onClick = { results.getCovidCases() }) {
                    Text(text = "Show Results", fontSize = 60.sp)
                }
                Text(text = results.results ?: "empty", fontSize = 20.sp)
            }
        }
    }
}


