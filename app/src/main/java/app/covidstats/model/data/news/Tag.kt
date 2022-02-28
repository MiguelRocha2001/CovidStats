package app.covidstats.model.data.news

data class Tag(
    val id: Int,
    val images: ImagesX,
    val isObsession: Boolean,
    val slug: String,
    val sponsor: Any,
    val webTitle: String
)