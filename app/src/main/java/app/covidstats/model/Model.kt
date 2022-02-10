package app.covidstats.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.Fuel

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// TODO use this API: https://disease.sh/docs/?urls.primaryName=version%203.0.0

private const val WORLD_CASES_URI = "https://disease.sh/v3/covid-19/all"

/** @return String URI for covid-19 stats for [continent] */
private fun String.getContinentUri(continent: String) = "https://disease.sh/v3/covid-19/continents/${continent}?strict=true"

/**
 * Represents a JSON object that holds data for Portugal covid stats
 */
@Serializable
data class Data(val date_stamp: String, val cnt_confirmed: Int, val cnt_death: Int, val cnt_recovered: Int)

/**
 * Represents a JSON object that holds data for world covid stats
 */
@Serializable
data class WorldData(val updated: Long? = null,
                     val cases: Long? = null,
                     val todayCases: Long? = null,
                     val deaths: Long? = null,
                     val todayDeaths: Long? = null,
                     val recovered: Long? = null,
                     val todayRecovered: Long? = null,
                     val active: Long? = null,
                     val critical: Long? = null,
                     val casesPerOneMillion: Double? = null,
                     val deathsPerOneMillion: Double? = null,
                     val tests: Long? = null,
                     val testsPerOneMillion: Double? = null,
                     val population: Long? = null,
                     val oneCasePerPeople: Long? = null,
                     val oneDeathPerPeople: Long? = null,
                     val oneTestPerPeople: Long? = null,
                     val activePerOneMillion: Double? = null,
                     val recoveredPerOneMillion: Double? = null,
                     val criticalPerOneMillion: Double? = null,
                     val affectedCountries: Long? = null
)

class Model() {
    var results by mutableStateOf<WorldData?>(null)
    var location by mutableStateOf<String?>(null)

    fun getWorldCovidStats() {
        Fuel.get(WORLD_CASES_URI)
            .response { request, response, result ->
                // to show body response
                // Log.v("response", result.get().decodeToString())
                results = json.decodeFromString<WorldData>(result.get().decodeToString())
                // to show json object with response
                // Log.v("response", obj.toString())
            }
    }

    private val json = Json { ignoreUnknownKeys = true }

    fun getContinentCovidStats(continent: String) {
        Fuel.get(String().getContinentUri(continent))
            .response { request, response, result ->
                results = json.decodeFromString<WorldData>(result.get().decodeToString())
                location = continent
            }
    }

    fun emptyResults() {
        results = null
    }
}
