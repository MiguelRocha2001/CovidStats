package app.covidstats.model.data.app

import app.covidstats.error.AppError

open class Locations
class LocationsSuccess(val locations: List<String>): Locations()
object LocationsLoading: Locations()
class LocationsError(val error: AppError): Locations()
