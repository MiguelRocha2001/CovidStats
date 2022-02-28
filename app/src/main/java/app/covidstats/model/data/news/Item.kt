package app.covidstats.model.data.news

data class Item(
    val id: Int,
    val images: Images,
    val lastModified: String,
    val lead: String,
    val links: Links,
    val liveblog: Any,
    val metadata: Metadata,
    val premium: Boolean,
    val pubDate: String,
    val slug: String,
    val title: Title,
    val type: String
)