package app.covidstats.model.continents

import app.covidstats.R

class NorthAmerica(): Continent("north_america") {
    override val imageRes = R.drawable.north_america

    override val countries = listOf(Canada(), Usa(), Mexico())

    class Canada(override val imageRes: Int = R.drawable.canada): Country("canada")
    class Usa(override val imageRes: Int = R.drawable.united_states_of_america): Country("usa")
    class Mexico(override val imageRes: Int = R.drawable.mexico): Country("mexico")

}