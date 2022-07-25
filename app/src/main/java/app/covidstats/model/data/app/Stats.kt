package app.covidstats.model.data.app

import app.covidstats.error.AppError
import app.covidstats.model.data.covid_stats.CovidStats
import kotlinx.serialization.Serializable

@Serializable
open class Stats(val name: String): java.io.Serializable

@Serializable
class StatsSuccess(val location: String, val data: CovidStats) : Stats(location)


class StatsError(val error: AppError, name: String) : Stats(name)

class StatsLoading(name: String) : Stats(name)