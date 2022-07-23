package app.covidstats.model.opers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.covidstats.model.Storage

class FavoriteOpers(
    private val storage: Storage
) {

    internal var favoriteLocations by mutableStateOf<List<String>>(emptyList())

    init {
        favoriteLocations = storage.getFavoriteCountries()
    }

    /**
     * Adds [location] to the list of favorite locations.
     */
    fun addFavoriteLocation(location: String) {
        val favorites = favoriteLocations.toMutableList()
        favorites.add(location)
        favoriteLocations = favorites
        // stores favorites
        storage.saveFavoriteCountries(favoriteLocations)
    }

    /**
     * Removes [location] (if exists) to the list of favorite locations.
     */
    fun removeFavoriteLocation(location: String) {
        val favorites = favoriteLocations.toMutableList()
        favorites.remove(location)
        favoriteLocations = favorites
        // stores favorites
        storage.saveFavoriteCountries(favoriteLocations)
    }

    /**
     * Filters current list of countries by the given [filter].
     */
    fun filterFavorites(name: String) {
        favoriteLocations = storage.getFavoriteCountries()
        val observedFavorites = favoriteLocations
        if (name.length < 3) return
        favoriteLocations = observedFavorites.filter { it.contains(name, true) }
    }
}