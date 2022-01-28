package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.uris.WorldData

val AMBAR = Color(225, 172, 27)


@Composable
fun ShowWorldCovidStats(covidStats: WorldData?) {
    if (covidStats != null) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "World Covid Stats", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(20.dp))
            printText("Updated", covidStats.updated)
            printText("Cases", covidStats.cases)
            printText("Deaths", covidStats.deaths)
            printText("Today Cases", covidStats.todayCases)
            printText("Today Deaths", covidStats.todayDeaths)
            printText("Recovered", covidStats.recovered)
            printText("Today Recovered", covidStats.todayRecovered)
            printText("Active", covidStats.active)
            printText("Critical", covidStats.critical)
            printText("Cases Per Million", covidStats.casesPerOneMillion)
            printText("Deaths Per Million", covidStats.deathsPerOneMillion)
            printText("Tests", covidStats.tests)
            printText("Population", covidStats.population)
            printText("One Case Per People", covidStats.oneCasePerPeople)
            printText("One Death Per People", covidStats.oneDeathPerPeople)
            printText("One Test Per People", covidStats.oneTestPerPeople)
            printText("Active Per Million", covidStats.activePerOneMillion)
            printText("Recover Per One Million", covidStats.recoveredPerOneMillion)
            printText("Critical Per One Million", covidStats.criticalPerOneMillion)
            printText("Affected Countries", covidStats.affectedCountries)
        }
    }
}

@Composable
fun printText(str: String, value: Any) {
    Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
        Text(text = "$str ", fontSize = 20.sp, color = AMBAR)
        Text(text = "$value", fontSize = 20.sp)
    }
}