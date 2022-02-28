package app.covidstats.db

import app.covidstats.model.data.news.News
import retrofit2.Response
import retrofit2.http.GET

// API for news: https://developer.nytimes.com/docs/articlesearch-product/1/overview
// api-key=nbjbXtDnNGxMOzknjSRi6zYzmOxGUPL7

// use: https://eco.sapo.pt/wp-json/eco/v1/lists/featured

interface NewsApi {

    @GET("lists/featured/")
    suspend fun fetchNews(): Response<News>
}