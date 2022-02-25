package app.covidstats.model.continents

import androidx.compose.ui.graphics.painter.Painter

abstract class Continent(val string: String) {
    abstract val imageRes: Int
    abstract val countries: List<Country>
}
abstract class Country(val string: String) {
    abstract val imageRes: Int
}
