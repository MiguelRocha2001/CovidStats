package app.covidstats.Ui

sealed class Screen(val route: String) {
    object ShowWorldCovidStats: Screen(route = "world_stats")
    object DisplayContinentOptions: Screen(route = "continent_options")
    object Continents: Screen(route = "continents")
    object News: Screen(route = "news")
    object Info: Screen(route = "info")
}