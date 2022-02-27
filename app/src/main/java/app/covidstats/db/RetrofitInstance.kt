package app.covidstats.db

import app.covidstats.model.data.World
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: World by lazy {
        Retrofit.Builder()
            .baseUrl("https://disease.sh")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(World::class.java)
    }
}