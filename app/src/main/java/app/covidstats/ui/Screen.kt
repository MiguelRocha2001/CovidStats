package app.covidstats.ui

sealed class Screen(val route: String) {
    object MainPage: Screen(route = "main_page")
    object ShowWorldCovidStats: Screen(route = "stats")
    object Continents: Screen(route = "continents")
    object DisplayContinentOptions: Screen(route = "continent_options")
    object News: Screen(route = "news")
    object Info: Screen(route = "info")
    object Wait: Screen(route = "wait")
}