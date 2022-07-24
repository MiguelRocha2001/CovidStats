package app.covidstats.db

import android.util.Log
import app.covidstats.model.data.app.replaceSeparatorAndToLowercase
import app.covidstats.model.data.covid_stats.*
import app.covidstats.model.data.news.Item
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request

@OptIn(ExperimentalSerializationApi::class)
private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

/**
 * Fetches Covid-19 stats worldwide.
 */
internal fun getWorldStats(): CovidStats {
    val request = Request(
        Method.GET,
        "https://disease.sh/v3/covid-19/all")
    val response = requestApi(request)
    return json.decodeFromString(response.bodyString())
}

/**
 * Fetches Covid-19 stats for [continent].
 */
internal fun getContinentStats(continent: app.covidstats.model.data.app.Continent): CovidStats {
    Log.i("DataOut", continent.toApiString())
    val request = Request(
        Method.GET,
        "https://disease.sh/v3/covid-19/continents/${continent.toApiString()}?strict=true"
    )
    val response = requestApi(request)
    return json.decodeFromString(response.bodyString())
}

/**
 * Fetches Covid-19 stats for [country].
 */
internal fun getCountryStats(country: String): CovidStats {
    val request = Request(
        Method.GET,
        // replace spaces with %20
        "https://disease.sh/v3/covid-19/countries/${country.replace(" ", "%20")}?strict=true"
    )
    val response = requestApi(request)
    return json.decodeFromString(response.bodyString())
}

/**
 * @return a List with all available continents to fetch stats.
 */
internal fun fetchAllContinents(): List<String> {
    val request = Request(Method.GET, "https://disease.sh/v3/covid-19/continents")
    val response = requestApi(request)
    val continents = json.decodeFromString<List<Continent>>(response.bodyString())
    return continents.map { it.continent }
}

/**
 * @return a List with all available continents to fetch stats or null if servers responds with error.
 */
internal fun fetchContinentCountries(continent: app.covidstats.model.data.app.Continent): List<String> {
    val request = Request(
        Method.GET,
        "https://disease.sh/v3/covid-19/continents/${continent.toApiString()}?strict=true"
    )
    val response = requestApi(request)
    val decodedContinent = json.decodeFromString<Continent>(response.bodyString())
    return decodedContinent.countries
}

/**
 * Fetches portuguese news related to covid-19
 */
internal fun getCovidNews(): List<Item> {
    val request = Request(Method.GET, "https://eco.sapo.pt/wp-json/eco/v1/")
    val response = client(request)
    val items = mutableListOf<Item>()
    /*
    response.body.forEach { newsItem ->
        newsItem.collection.forEach { collection ->
            val slug = collection.item.slug
            val title = collection.item.title.short
            if ("covid" in slug || "covid" in title)
                items.add(collection.item)
        }
    }
     */
    return items
}

/**
 * This function allows, given a Continent, to get the continent string that is used in the API.
 * @return the continent string for the API
 */
fun app.covidstats.model.data.app.Continent.toApiString() =
    if (this === app.covidstats.model.data.app.Continent.AUSTRALIA_OCEANIA) "Australia-Oceania"
    else this.name.replaceSeparatorAndToLowercase().replace(" ", "%20")
