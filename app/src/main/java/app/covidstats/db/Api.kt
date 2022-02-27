package app.covidstats.db

import app.covidstats.model.data.World
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    /* Uses API key
    @GET("/v3/covid-19/all")
    suspend fun getWorldStats(@Query("key") key: String): Response<World>
     */

    @GET("/all")
    suspend fun getWorldStats(): Response<World>

    @GET("/continents/{continent}?strict=true")
    suspend fun getContinentStats(@Path("continent") continent: String): Response<World>

    @GET("/countries/{country}?strict=true")
    suspend fun getCountryStats(@Path("country") country: String): Response<World>
}