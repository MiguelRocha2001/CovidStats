package app.covidstats.model.data.news

data class Metadata(
    val commentCount: Int,
    val contributors: List<Contributor>,
    val shareCount: Int,
    val tags: List<Tag>
)