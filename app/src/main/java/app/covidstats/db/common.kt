package app.covidstats.db

import android.util.Log
import app.covidstats.error.ClientError
import app.covidstats.error.InternalError
import app.covidstats.error.ServerError
import org.http4k.client.ApacheClient
import org.http4k.core.Request
import org.http4k.core.Response

val client = ApacheClient()

/**
 * A function that takes a [Request] and returns a [Response].
 * This will handle possible errors.
 * @returns A [Response] if there is no error
 * @throws ServerError if the server returns an error relating to the server
 * @throws ClientError if the server returns an error relating to the client (ex: Bad Request)
 * @throws InternalError for other reason.
 */
internal fun requestApi(request: Request): Response {
    val response = client(request)
    val resStatus = response.status
    if (resStatus.successful) {
        Log.i("API", "Successful response")
        return response
    } else {
        if (resStatus.serverError) {
            Log.e("API", "Server error")
            throw ServerError()
        }
        else if (resStatus.clientError) {
            Log.e("API", "Client error")
            throw ClientError()
        }
        else {
            Log.e("API", "Internal error")
            throw InternalError()
        }
    }
}