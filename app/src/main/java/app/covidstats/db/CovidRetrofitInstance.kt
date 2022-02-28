package app.covidstats.db

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CovidRetrofitInstance {
    val api: CovidApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://disease.sh/v3/covid-19/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CovidApi::class.java)
    }
}

