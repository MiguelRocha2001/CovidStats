package app.covidstats.db

import app.covidstats.model.data.covid_stats.Continent
import app.covidstats.model.data.covid_stats.Continents
import app.covidstats.model.data.covid_stats.Country
import app.covidstats.model.data.covid_stats.World
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// API for covid-19 stats: https://disease.sh/docs/?urls.primaryName=version%203.0.0

interface CovidApi {

    @GET("all")
    suspend fun getWorldStats(): Response<World>

    @GET("continents")
    suspend fun getAllContinents(@Query("strict") strict: Boolean): Response<Continents>

    @GET("continents/{continent}")
    suspend fun getContinentStats(@Path("continent") continent: String, @Query("strict") strict: Boolean): Response<Continent>

    @GET("countries/{country}")
    suspend fun getCountryStats(@Path("country") country: String, @Query("strict") strict: Boolean): Response<Country>
}