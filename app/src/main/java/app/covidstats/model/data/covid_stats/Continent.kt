package app.covidstats.model.data.covid_stats

data class Continent(
    val continent: String,
    val continentInfo: ContinentInfo,
    val countries: List<String>,
)