package app.covidstats.model.opers

import app.covidstats.model.Storage

/**
 * Adds [location] to the list of favorite locations to be passed in callback function.
 * This will also update the list of favorite locations in the [Storage] object.
 */
fun addFavoriteLocation(location: String, favoriteLocations: List<String>, storage: Storage, callback: (List<String>) -> Unit) {
    val favorites = favoriteLocations.toMutableList()
    favorites.add(location)
    // stores favorites
    storage.saveFavoriteCountries(favoriteLocations)
    callback(favorites)
}

/**
 * Removes [location] (if exists) from the list of favorite locations and passed in callback function.
 * This will also update the list of favorite locations in the [Storage] object.
 */
fun removeFavoriteLocation(location: String, favoriteLocations: List<String>, storage: Storage, callback: (List<String>) -> Unit) {
    val favorites = favoriteLocations.toMutableList()
    favorites.remove(location)
    // stores favorites
    storage.saveFavoriteCountries(favoriteLocations)
    callback(favorites)
}

/**
 * Filters current list of countries by the given [filter].
 */
fun filterFavorites(name: String, storage: Storage, callback: (List<String>) -> Unit) {
    val favLocationsFromStorage = storage.getFavoriteCountries()
    if (name.length < 2) return
    val filteredLocations = favLocationsFromStorage.filter { it.contains(name, true) }
    callback(filteredLocations)
}