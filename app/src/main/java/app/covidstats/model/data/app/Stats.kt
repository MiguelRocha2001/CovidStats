package app.covidstats.model.data.app

import app.covidstats.error.AppError
import app.covidstats.model.data.covid_stats.CovidStats

open class Stats(val name: String)
class StatsSuccess(val location: String, val data: CovidStats) : Stats(location)
class StatsError(val error: AppError, name: String) : Stats(name)
class StatsLoading(name: String) : Stats(name)