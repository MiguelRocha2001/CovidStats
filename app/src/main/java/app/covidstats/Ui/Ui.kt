package app.covidstats.Ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.covidstats.model.WorldData

val AMBAR_LIGHT = Color(225, 172, 27)
val AMBAR_DARK = Color(160, 122, 19)
val BLUE_LIGHT = Color(0,179, 179)
val BLUE_DARK = Color(0,102, 102)

@Composable
fun ShowWorldCovidStats(covidStats: WorldData?) {
    if (covidStats != null) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        ) {
            Text(
                text = "World Covid-19 Stats",
                fontSize = 31.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = AMBAR_DARK
            )
            Spacer(modifier = Modifier.height(20.dp))
            PrintText("Updated", covidStats.updated)
            PrintText("Cases", covidStats.cases)
            PrintText("Deaths", covidStats.deaths)
            PrintText("Today Cases", covidStats.todayCases!!)
            PrintText("Today Deaths", covidStats.todayDeaths)
            PrintText("Recovered", covidStats.recovered)
            PrintText("Today Recovered", covidStats.todayRecovered)
            PrintText("Active", covidStats.active)
            PrintText("Critical", covidStats.critical)
            PrintText("Cases Per Million", covidStats.casesPerOneMillion)
            PrintText("Deaths Per Million", covidStats.deathsPerOneMillion)
            PrintText("Tests", covidStats.tests)
            PrintText("Population", covidStats.population)
            PrintText("One Case Per People", covidStats.oneCasePerPeople)
            PrintText("One Death Per People", covidStats.oneDeathPerPeople)
            PrintText("One Test Per People", covidStats.oneTestPerPeople)
            PrintText("Active Per Million", covidStats.activePerOneMillion)
            PrintText("Recover Per One Million", covidStats.recoveredPerOneMillion)
            PrintText("Critical Per One Million", covidStats.criticalPerOneMillion)
            PrintText("Affected Countries", covidStats.affectedCountries)
        }
    }
}

@Composable
fun PrintText(str: String, value: Any?) {
    Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.Center) {
        Text(text = "$str ", fontSize = 20.sp, color = AMBAR_LIGHT)
        Text(text = if (value != null) "$value" else "in fault", fontSize = 20.sp, color = BLUE_LIGHT)
    }
}