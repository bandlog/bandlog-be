package net.effize.bandlog.auth.domain.exception

import net.effize.bandlog.common.exception.BandlogException
import net.effize.bandlog.common.exception.ErrorCode

class IllegalAuthenticationException : BandlogException(
    errorCode = ErrorCode.of("ILLEGAL_AUTHENTICATION"),
    userMessage = "인증 과정에 오류가 발생했습니다.",
    logMessage = "Illegal authentication error",
    httpStatusCode = 500
)
