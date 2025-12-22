package net.effize.bandlog.common.exception

open class BandlogException(
    val errorCode: ErrorCode,
    val userMessage: String,
    val logMessage: String,
    val httpStatusCode: Int,
    cause: Throwable? = null
) : RuntimeException(logMessage, cause)
