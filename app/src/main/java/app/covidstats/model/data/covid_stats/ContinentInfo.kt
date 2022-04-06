package app.covidstats.model.data.covid_stats

import kotlinx.serialization.Serializable

@Serializable
data class ContinentInfo(
    val lat: Double,
    val long: Double
)