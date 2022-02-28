package app.covidstats.model.data.news

data class TagX(
    val id: Int,
    val images: ImagesXX,
    val isObsession: Boolean,
    val slug: String,
    val sponsor: Sponsor,
    val webTitle: String
)