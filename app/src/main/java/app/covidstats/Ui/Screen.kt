package app.covidstats.Ui

sealed class Screen(val route: String) {
    object Menu: Screen(route = "menu")
    object ShowWorldCovidStats: Screen(route = "stats")
    object Continents: Screen(route = "continents")
    object DisplayContinentOptions: Screen(route = "continent_options")
    object News: Screen(route = "news")
    object Info: Screen(route = "info")
}