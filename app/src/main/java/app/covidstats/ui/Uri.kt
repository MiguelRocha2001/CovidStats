package app.covidstats.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.Fuel

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class Data(val date_stamp: String, val cnt_confirmed: Int, val cnt_death: Int, val cnt_recovered: Int)

const val CASES_URI = "https://covid19.richdataservices.com/rds/api/query/int/jhu_country/select?cols=date_stamp,cnt_confirmed,cnt_death,cnt_recovered&where=(iso3166_1=PT)&format=amcharts&limit=5"

@Serializable
data class DataProvider(val dataProvider: Array<Data>)

class Model() {
    var results by mutableStateOf<String?>(null)

    fun getCovidCases() {
        Fuel.get(CASES_URI)
            .response { request, response, result ->
                // to show body response
                //Log.v("response", result.get().decodeToString())
                val obj = Json.decodeFromString<DataProvider>(result.get().decodeToString())
                // to show json object with response
                //Log.v("response", obj.toString())
                results = obj.toString()
            }
    }
}
