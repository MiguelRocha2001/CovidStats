package app.covidstats.model.continents

import androidx.compose.ui.res.painterResource
import app.covidstats.R

class NorthAmerica(): Continent("north_america") {
    override val imageRes = R.drawable.north_america

    override val countries = listOf(Canada, Usa, Mexico)

    object Canada: Country("canada")
    object Usa: Country("usa")
    object Mexico: Country("mexico")

}