package app.covidstats.error

open class AppError: Throwable()

class InternalError: AppError()

class ServerError: AppError()

class ClientError: AppError()
