package app.covidstats.model.data.news

data class NewsItem(
    val breakingNews: Boolean,
    val collection: List<Collection>,
    val section: String,
    val stockWidget: StockWidget,
    val tag: TagX,
    val title: String
)