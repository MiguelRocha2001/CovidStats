package app.covidstats.uris

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.Fuel

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// TODO use this API: https://disease.sh/docs/?urls.primaryName=version%203.0.0

const val WORLD_CASES_URI = "https://disease.sh/v3/covid-19/all"

/**
 * Represents a JSON object that holds data for Portugal covid stats
 */
@Serializable
data class Data(val date_stamp: String, val cnt_confirmed: Int, val cnt_death: Int, val cnt_recovered: Int)

/**
 * Represents a JSON object that holds data for world covid stats
 */
@Serializable
data class WorldData(val updated: Long,
                     val cases: Long,
                     val todayCases: Long,
                     val deaths: Long,
                     val todayDeaths: Long,
                     val recovered: Long,
                     val todayRecovered: Long,
                     val active: Long,
                     val critical: Long,
                     val casesPerOneMillion: Long,
                     val deathsPerOneMillion: Double,
                     val tests: Long,
                     val testsPerOneMillion: Double,
                     val population: Long,
                     val oneCasePerPeople: Long,
                     val oneDeathPerPeople: Long,
                     val oneTestPerPeople: Long,
                     val activePerOneMillion: Double,
                     val recoveredPerOneMillion: Double,
                     val criticalPerOneMillion: Double,
                     val affectedCountries: Long
)

class Model() {
    var results by mutableStateOf<WorldData?>(null)

    fun getCovidCases() {
        Fuel.get(WORLD_CASES_URI)
            .response { request, response, result ->
                // to show body response
                //Log.v("response", result.get().decodeToString())
                results = Json.decodeFromString<WorldData>(result.get().decodeToString())
                // to show json object with response
                //Log.v("response", obj.toString())
            }
    }
}
