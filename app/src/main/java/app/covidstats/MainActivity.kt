package app.covidstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.uris.DataProvider
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
                ShowCovidStats(covidStats.results)
            }
        }
    }
}

@Composable
private fun ShowCovidStats(covidStats: DataProvider?) {
    LazyColumn() {
        val stats = covidStats
        stats?.dataProvider?.forEach { covidStats ->
            item {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Text(text = "date: ${covidStats.date_stamp}", fontSize = 20.sp)
                    Text(text = "country: ${covidStats.iso3166_1}", fontSize = 20.sp)
                    Text(text = "cases: ${covidStats.cnt_confirmed}", fontSize = 20.sp)
                    //Text(text = "deaths: ${covidStats.cnt_death}", fontSize = 20.sp)
                    //Text(text = "recovered: ${covidStats.cnt_recovered}", fontSize = 20.sp)
                }
            }
        }
    }
}

