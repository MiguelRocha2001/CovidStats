package app.covidstats.model.data.app

import app.covidstats.error.AppError
import kotlinx.serialization.Serializable

open class Locations

@Serializable
class LocationsSuccess(val locations: List<String>): Locations()

@Serializable
object LocationsLoading: Locations()


class LocationsError(val error: AppError): Locations()
