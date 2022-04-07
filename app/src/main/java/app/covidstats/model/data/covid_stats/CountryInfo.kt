package app.covidstats.model.data.covid_stats

import kotlinx.serialization.Serializable

@Serializable
data class CountryInfo(
    val _id: Int?,
    val flag: String?,
    val iso2: String?,
    val iso3: String?,
    val lat: Float,
    val long: Float
)