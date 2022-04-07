package app.covidstats.db

import app.covidstats.model.data.covid_stats.*
import app.covidstats.model.data.news.Item
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.http4k.client.ApacheClient
import org.http4k.core.Method

private val json = Json { ignoreUnknownKeys = true }

/**
 * Fetches Covid-19 stats worldwide.
 */
internal fun getWorldStats(): CovidStats {
    val client = ApacheClient()
    val request = Request(
        Method.GET,
        "https://disease.sh/v3/covid-19/all")
    val response = client(request)
    return json.decodeFromString(response.bodyString())
}

/**
 * Fetches Covid-19 stats for [continent].
 */
internal fun getContinentStats(continent: String): CovidStats {
    val client = ApacheClient()
    val request = Request(
        Method.GET,
        "https://disease.sh/v3/covid-19/continents/$continent?strict=true"
    )
    val response = client(request)
    return json.decodeFromString(response.bodyString())
}

/**
 * Fetches Covid-19 stats for [country].
 */
internal fun getCountryStats(country: String): CovidStats {
    val client = ApacheClient()
    val request = Request(
        Method.GET,
        "https://disease.sh/v3/covid-19/countries/$country?strict=true"
    )
    val response = client(request)
    return json.decodeFromString(response.bodyString())
}

/**
 * @return a List with all available continents to fetch stats.
 */
internal fun fetchAllContinents(): List<String> {
    val client = ApacheClient()
    val request = Request(Method.GET, "https://disease.sh/v3/covid-19/continents")
    val response = client(request)
    val continents = json.decodeFromString<List<Continent>>(response.bodyString())
    return continents.map { it.continent }
}

/**
 * @return a List with all available continents to fetch stats.
 */
internal fun fetchAllCountries(continent: String): List<String> {
    val client = ApacheClient()
    val request = org.http4k.core.Request(Method.GET, "https://disease.sh/v3/covid-19/countries")
    val response = client(request) // TODO -> fails here!!!
    val countries = Json.decodeFromString<List<Country>>(response.bodyString())
    return countries.filter { it.continent == continent }.map { it.country }
}

/**
 * Fetches portuguese news related to covid-19
 */
internal suspend fun getCovidNews(): List<Item>? {
    val client = ApacheClient()
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
    throw InternalError()
}