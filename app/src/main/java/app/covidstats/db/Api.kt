package app.covidstats.db

import app.covidstats.model.data.World
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/v3/covid-19/all")
    suspend fun getWorldStats(@Query("key") key: String): Response<World>
}