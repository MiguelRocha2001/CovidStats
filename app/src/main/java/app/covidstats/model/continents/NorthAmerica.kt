package app.covidstats.model.continents

import app.covidstats.model.Continent

enum class NorthAmerica {
    CANADA, USA, MEXICO;
    val continent: Continent = Continent.NORTH_AMERICA
}