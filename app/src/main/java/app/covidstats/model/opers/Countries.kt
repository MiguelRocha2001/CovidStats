package app.covidstats.model.opers

import android.util.Log
import app.covidstats.db.fetchContinentCountries
import app.covidstats.error.AppError
import app.covidstats.model.Storage
import app.covidstats.model.data.other.Continent
import app.covidstats.model.data.other.formattedName
import app.covidstats.model.data.other.toContinent
import app.covidstats.model.data.time.TimeZone

/**
 * Sets global variable countries with the obtained list of countries, given a [continent].
 * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
 */
fun loadContinentCountries(continent: Continent, storage: Storage, callback: (Pair<String, List<String>>) -> Unit) {
    val countries = loadContinentCountriesInternal(continent, storage)
    callback(countries)
}

fun loadContinentCountriesInternal(
    continent: Continent,
    storage: Storage
): Pair<String, List<String>> {
    val locations = storage.getContinentLocations(continent)
    if (locations != null) {
        val expired = locations.first.timeExpired()
        if (expired) {
            Log.i("Model", "Continent ${continent.formattedName()} expired")
        } else {
            val countries = continent.formattedName() to locations.second
            Log.i("Model", "${continent.formattedName()} countries loaded from cache")
            return countries
        }
    }
    return fetchSaveAndGetCountries(continent, storage)
}

private fun fetchSaveAndGetCountries(continent: Continent, storage: Storage): Pair<String, List<String>>  {
    Log.i("Model", "Fetching locations, for $continent, from API")
    try {
        val locations = fetchContinentCountries(continent)
        Log.i("Model", "Locations fetched successfully from API")
        Log.i("Model", "Saving locations, for $continent, to storage")
        storage.saveContinentLocations(continent, locations, TimeZone())
        Log.i("Model", "Saved locations, for $continent, to storage")
        return continent.formattedName() to locations
    } catch (e: AppError) {
        Log.e("Model", "Error fetching locations from API", e)
        throw e // TODO: handle error
    }
}

/**
 * Filters current list of countries by the given [filter].
 */
fun filterLocations(name: String, storage: Storage, countries: Pair<String, List<String>>, callback: (List<String>) -> Unit) {
    // val observedCountries = countries ?: return
    // val continent = observedCountries.first.toContinent() ?: return
    if (name.length < 3) return callback(countries.second)
    val continent = countries.first.toContinent() ?: return
    val loadedCountries = loadContinentCountriesInternal(continent, storage)
    val filteredCountries = loadedCountries.first to
            loadedCountries.second.filter { it.contains(name, true) }
    callback(filteredCountries.second)
}