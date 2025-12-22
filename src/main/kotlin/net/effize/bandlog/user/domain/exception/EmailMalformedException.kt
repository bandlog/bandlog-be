package net.effize.bandlog.user.domain.exception

import net.effize.bandlog.common.exception.BandlogException
import net.effize.bandlog.common.exception.ErrorCode

class EmailMalformedException : BandlogException(
    errorCode = ErrorCode.of("EMAIL_MALFORMED_ERROR"),
    userMessage = "이메일 형식이 잘못되었습니다.",
    logMessage = "email malformed",
    httpStatusCode = 400
)
