package app.covidstats.model.continents

class NorthAmerica(): Continent() {
    val values = listOf(Canada, Usa, Mexico)

    object Canada: Country("canada")
    object Usa: Country("usa")
    object Mexico: Country("mexico")

}