package app.covidstats.model

import android.content.Context
import java.io.File

class Storage(private val context: Context) {
    private val FILE_NAME = "covid_stats.txt"
    private val file = File(context.filesDir, FILE_NAME)

    /**
     * Should be called when the app is started and in a coroutine.
     */
    fun init() {
        if (!file.exists())
            file.createNewFile()
    }

    /**
     * Stores [favoriteCountries] locally.
     */
    fun saveFavoriteCountries(favoriteCountries: List<String>) {
        file.writeText(favoriteCountries.toString())
    }

    /**
     * Returns the favorite countries stored locally.
     */
    fun getFavoriteCountries(): List<String>? {
        val countries = file.readText().split(",").toList()
        return if (countries.isNullOrEmpty()) null
        else countries
    }
}