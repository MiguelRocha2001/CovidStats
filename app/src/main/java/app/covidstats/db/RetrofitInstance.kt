package app.covidstats.db

import app.covidstats.model.data.World
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl("https://disease.sh/v3/covid-19")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}