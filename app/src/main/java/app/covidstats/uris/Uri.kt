package app.covidstats.uris

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.Fuel

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class DataProvider(val dataProvider: Array<Data2>)

/**
 * Represents a JSON object that holds data for Portugal covid stats
 */
@Serializable
data class Data1(val date_stamp: String, val cnt_confirmed: Int, val cnt_death: Int, val cnt_recovered: Int)

/**
 * Represents a JSON object that holds data for world covid stats
 */
@Serializable
data class Data2(val date_stamp: String, val iso3166_1: String, val cnt_confirmed: Int)

const val CASES_URI = "https://covid19.richdataservices.com/rds/api/query/int/jhu_country/select?cols=date_stamp,cnt_confirmed,cnt_death,cnt_recovered&where=(iso3166_1=PT)&format=amcharts&limit=10"
const val WORLD_CASES_URI = "https://covid19.richdataservices.com/rds/api/query/int/jhu_country/tabulate?dims=date_stamp,iso3166_1&where=(year_stamp=2020) AND (iso3166_1=US OR iso3166_1=CA OR iso3166_1=ES OR iso3166_1=IT OR iso3166_1=CN)&measure=cnt_confirmed:SUM(cnt_confirmed)&inject=true&metadata=true&orderBy=date_stamp ASC,iso3166_1 ASC&totals=true&format=amcharts"

class Model() {
    var results by mutableStateOf<DataProvider?>(null)

    fun getCovidCases() {
        Fuel.get(WORLD_CASES_URI)
            .response { request, response, result ->
                // to show body response
                //Log.v("response", result.get().decodeToString())
                results = Json.decodeFromString<DataProvider>(result.get().decodeToString())
                // to show json object with response
                //Log.v("response", obj.toString())
            }
    }
}
