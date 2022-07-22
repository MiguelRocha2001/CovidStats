package app.covidstats.model.data.covid_stats

fun getPropertiesUnit(property: String): String {
    return when (property) {
        "active", "activePerMillion, critical, criticalPerMillion" -> {
            "People"
        }
        "cases" -> {
            "Cases"
        }
        "deaths" -> {
            "Deaths"
        }
        "recovered" -> {
            "Recovered"
        }
        else -> {
            "Cases"
        }
    }
}