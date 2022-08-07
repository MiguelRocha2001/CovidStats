package app.covidstats.model.opers.countries

import android.util.Log
import app.covidstats.db.fetchContinentCountries
import app.covidstats.error.AppError
import app.covidstats.model.Storage
import app.covidstats.model.data.app.*

/**
 * Sets global variable countries with the obtained list of countries, given a [continent].
 * If the data is already in cache, obtains it from there. Otherwise, requests the API, and stores the data in cache to further use.
 */
fun loadContinentCountries(continent: Continent, storage: Storage, callback: (Locations) -> Unit) {
    val countries = loadContinentCountriesInternal(continent, storage)
    callback(countries)
}

fun loadContinentCountriesInternal(
    continent: Continent,
    storage: Storage
): Locations {
    val locations = storage.getContinentLocations(continent)
    if (locations != null) {
        val expired = locations.first.timeExpired()
        if (expired) {
            Log.i("Model", "Continent ${continent.formattedName()} expired")
        } else {
            val countries = locations.second
            Log.i("Model", "${continent.formattedName()} countries loaded from cache")
            return LocationsSuccess(countries)
        }
    }
    return fetchSaveAndGetCountries(continent, storage)
}

private fun fetchSaveAndGetCountries(continent: Continent, storage: Storage): Locations {
    Log.i("Model", "Fetching locations, for $continent, from API")
    return try {
        val locations = fetchContinentCountries(continent)
        Log.i("Model", "Locations fetched successfully from API")
        Log.i("Model", "Saving locations, for $continent, to storage")
        storage.saveContinentLocations(continent, locations, TimeZone())
        Log.i("Model", "Saved locations, for $continent, to storage")
        LocationsSuccess(locations)
    } catch (e: AppError) {
        Log.e("Model", "Error fetching locations from API", e)
        LocationsError(e)
    }
}

/**
 * Filters current list of countries by the given [filter].
 */
fun filterLocations(name: String, storage: Storage, continent: Continent, callback: (Locations) -> Unit) {
    val loadedCountries = loadContinentCountriesInternal(continent, storage)
    if (loadedCountries is LocationsError) {
        return callback(loadedCountries)
    }
    if (name.length < 2) return callback(loadedCountries)
    if (loadedCountries is LocationsSuccess) {
        val filteredCountries = loadedCountries.locations.filter { it.contains(name, true) }
        callback(LocationsSuccess(filteredCountries))
    }
}