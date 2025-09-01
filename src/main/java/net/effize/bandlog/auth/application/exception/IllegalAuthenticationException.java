package net.effize.bandlog.auth.application.exception;

import net.effize.bandlog.common.exception.BandlogException;
import net.effize.bandlog.common.exception.ErrorCode;

public class IllegalAuthenticationException extends BandlogException {
    public IllegalAuthenticationException() {
        super(ErrorCode.of("ILLEGAL_AUTHENTICATION"), "인증 과정에 오류가 발생했습니다.", "Illegal authentication error", 500);
    }
}
