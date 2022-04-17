package app.covidstats.model.data.covid_stats

import kotlinx.serialization.Serializable

@Serializable
data class CovidStats(
    val active: Int,
    val activePerOneMillion: Double,
    val cases: Int,
    val casesPerOneMillion: Double,
    val critical: Int,
    val criticalPerOneMillion: Double,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val oneCasePerPeople: Int,
    val oneDeathPerPeople: Int,
    val oneTestPerPeople: Int,
    val population: Long,
    val recovered: Int,
    val recoveredPerOneMillion: Double,
    val tests: Long,
    val testsPerOneMillion: Double,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long
)