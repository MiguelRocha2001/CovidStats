package app.covidstats.model

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.security.AccessController.getContext

class Storage {
    private val context = LocalContext
    private val file = File(context, "covid.json")

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