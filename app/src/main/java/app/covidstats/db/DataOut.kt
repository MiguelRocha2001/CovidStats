package app.covidstats.db

import app.covidstats.model.data.covid_stats.Continent
import app.covidstats.model.data.covid_stats.Country
import app.covidstats.model.data.covid_stats.World
import app.covidstats.model.data.news.Collection
import app.covidstats.model.data.news.Item
import app.covidstats.model.data.news.News
import app.covidstats.model.data.news.NewsItem

val statsService = CovidRetrofitInstance.api
val newsService = NewsRetrofitInstance.api

/**
 * Fetches Covid-19 stats worldwide.
 */
internal suspend fun getWorldStats(): World? {
    val call = statsService.getWorldStats()
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}

/**
 * @return a List with all available continents to fetch stats.
 */
internal suspend fun loadAllContinents(): List<String> {
    val call = statsService.getAllContinents(strict = true)
    if (call.isSuccessful) {
        val continentsRsp = call.body() ?: return emptyList()
        return continentsRsp.map { continent -> continent.continent }
    }
    throw InternalError()
}

/**
 * @return a List with all available continents to fetch stats.
 */
internal suspend fun loadAllCountries(continent: String): List<String> =
    getContinentStats(continent)?.countries ?: emptyList()

/**
 * Fetches Covid-19 stats for [continent].
 */
internal suspend fun getContinentStats(continent: String): Continent? {
    val call = statsService.getContinentStats(continent, strict = true)
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}

/**
 * Fetches Covid-19 stats for [country].
 */
internal suspend fun getCountryStats(country: String): Country? {
    val call = statsService.getCountryStats(country, strict = true)
    if (call.isSuccessful)
        return call.body()
    throw InternalError()
}

/**
 * Fetches portuguese news related to covid-19
 */
internal suspend fun getCovidNews(): List<Item>? {
    val call = newsService.fetchNews()
    if (call.isSuccessful) {
        val body = call.body() ?: return null
        val items = mutableListOf<Item>()
        body.forEach { newsItem ->
            newsItem.collection.forEach { collection ->
                val slug = collection.item.slug
                val title = collection.item.title.short
                if ("covid" in slug || "covid" in title)
                    items.add(collection.item)
            }
        }
        return items
    }
    throw InternalError()
}