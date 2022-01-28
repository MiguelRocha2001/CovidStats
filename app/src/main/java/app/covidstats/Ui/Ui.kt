package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.uris.WorldData


@Composable
fun ShowWorldCovidStats(covidStats: WorldData?) {
    if (covidStats != null) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "World Covid Stats", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Updated: ${covidStats.updated}", fontSize = 20.sp)
            Text(text = "Cases: ${covidStats.cases}", fontSize = 20.sp)
            Text(text = "Deaths: ${covidStats.deaths}", fontSize = 20.sp)
            Text(text = "Today Cases: ${covidStats.todayCases}", fontSize = 20.sp)
            Text(text = "Today Deaths: ${covidStats.todayDeaths}", fontSize = 20.sp)
            Text(text = "Recovered: ${covidStats.recovered}", fontSize = 20.sp)
            Text(text = "Today Recovered: ${covidStats.todayRecovered}", fontSize = 20.sp)
            Text(text = "Active: ${covidStats.active}", fontSize = 20.sp)
            Text(text = "Critical: ${covidStats.critical}", fontSize = 20.sp)
            Text(text = "Cases Per Million: ${covidStats.casesPerOneMillion}", fontSize = 20.sp)
            Text(text = "Deaths Per Million: ${covidStats.deathsPerOneMillion}", fontSize = 20.sp)
            Text(text = "Tests: ${covidStats.tests}", fontSize = 20.sp)
            Text(text = "Tests Per Million: ${covidStats.testsPerOneMillion}", fontSize = 20.sp)
            Text(text = "Population: ${covidStats.population}", fontSize = 20.sp)
            Text(text = "One Case Per People: ${covidStats.oneCasePerPeople}", fontSize = 20.sp)
            Text(text = "One Death Per People: ${covidStats.oneDeathPerPeople}", fontSize = 20.sp)
            Text(text = "One Test Per People: ${covidStats.oneTestPerPeople}", fontSize = 20.sp)
            Text(text = "Active Per Million: ${covidStats.activePerOneMillion}", fontSize = 20.sp)
            Text(text = "Recover Per One Million: ${covidStats.recoveredPerOneMillion}", fontSize = 20.sp)
            Text(text = "Critical Per One Million: ${covidStats.criticalPerOneMillion}",fontSize = 20.sp)
            Text(text = "Affected Countries: ${covidStats.affectedCountries}", fontSize = 20.sp)
        }
    }
}