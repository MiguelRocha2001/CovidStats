package app.covidstats.db

import app.covidstats.model.data.Continent
import app.covidstats.model.data.Country
import app.covidstats.model.data.World
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// API for covid-19 stats: https://disease.sh/docs/?urls.primaryName=version%203.0.0
// API for news: https://developer.nytimes.com/docs/articlesearch-product/1/overview

private const val WORLD_CASES_URI = "https://disease.sh/v3/covid-19/all"

/** @return String URI for covid-19 stats for [continent] */
private fun String.getContinentUri(continent: String) = "https://disease.sh/v3/covid-19/continents/${continent}?strict=true"

/** @return String URI for covid-19 stats for [country] */
private fun String.getCountryUri(country: String) = "https://disease.sh/v3/covid-19/countries/${country}?strict=true"

interface Api {

    /* Uses API key
    @GET("/v3/covid-19/all")
    suspend fun getWorldStats(@Query("key") key: String): Response<World>
     */

    @GET("all")
    suspend fun getWorldStats(): Response<World>

    @GET("continents/{continent}")
    suspend fun getContinentStats(@Path("continent") continent: String, @Query("strict") strict: Boolean): Response<Continent>

    @GET("countries/{country}")
    suspend fun getCountryStats(@Path("country") country: String, @Query("strict") strict: Boolean): Response<Country>
}