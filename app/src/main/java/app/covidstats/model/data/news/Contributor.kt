package app.covidstats.model.data.news

data class Contributor(
    val id: Int,
    val image: Image,
    val name: String,
    val role: String,
    val slug: String,
    val uri: String
)