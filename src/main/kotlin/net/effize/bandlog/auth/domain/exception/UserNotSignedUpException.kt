package net.effize.bandlog.auth.domain.exception

import net.effize.bandlog.common.exception.BandlogException
import net.effize.bandlog.common.exception.ErrorCode

class UserNotSignedUpException : BandlogException(
    errorCode = ErrorCode.of("NOT_SIGNED_UP"),
    userMessage = "Bandlog에 계정을 생성해 주세요.",
    logMessage = "User is not signed up yet",
    httpStatusCode = 401
)
