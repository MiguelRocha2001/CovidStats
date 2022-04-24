package app.covidstats.db

import app.covidstats.model.data.covid_stats.*
import app.covidstats.model.data.news.Item
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request

@OptIn(ExperimentalSerializationApi::class)
private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

val client = ApacheClient()

/**
 * Fetches Covid-19 stats worldwide.
 */
internal fun getWorldStats(): CovidStats {
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
    val request = Request(
        Method.GET,
        "https://disease.sh/v3/covid-19/continents/${continent.replace(" ", "%20")}?strict=true"
    )
    val response = client(request)
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
    val response = client(request)
    return json.decodeFromString(response.bodyString())
}

/**
 * @return a List with all available continents to fetch stats.
 */
internal fun fetchAllContinents(): List<String> {
    val request = Request(Method.GET, "https://disease.sh/v3/covid-19/continents")
    val response = client(request)
    val continents = json.decodeFromString<List<Continent>>(response.bodyString())
    return continents.map { it.continent }
}

/**
 * @return a List with all available continents to fetch stats or null if servers responds with error.
 */
internal fun fetchContinentCountries(continent: String): List<String> {
    return try {
        val continentRequest = Request(Method.GET, "https://disease.sh/v3/covid-19/continents/${continent.replace(" ", "%20")}?strict=true")
        val continentResponse = client(continentRequest)
        val continent = json.decodeFromString<Continent>(continentResponse.bodyString())
        continent.countries
    } catch (e: Exception) {
        println("Error while fetching countries for $continent")
        emptyList()
    }
}

/**
 * @return a List with all available [countries] to fetch stats.
 */
internal fun fetchCountries(countries: List<String>): List<String> {
    var countriesRequestStr = "https://disease.sh/v3/covid-19/countries/"
    countries.forEachIndexed { index, country ->
        countriesRequestStr += if (index == 0) country.replace(" ", "%20")
        else "%2C%20${country.replace(" ", "%20")}"
    }
    //val countriesRequest = Request(Method.GET, "https://disease.sh/v3/covid-19/countries/Algeria%2C%20Angola%2C%20Benin%2C%20Botswana%2C%20Burkina%20Faso%2C%20Burundi%2C%20Cabo%20Verde")
    val countriesRequest = Request(Method.GET, countriesRequestStr)
    val countriesResponse = client(countriesRequest)
    return json.decodeFromString<List<Country>>(countriesResponse.bodyString()).map { it.country }
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