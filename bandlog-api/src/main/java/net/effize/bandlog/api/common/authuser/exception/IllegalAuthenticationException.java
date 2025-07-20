package net.effize.bandlog.api.common.authuser.exception;

import net.effize.bandlog.domain.common.exception.BandlogException;
import net.effize.bandlog.domain.common.exception.ErrorCode;

public class IllegalAuthenticationException extends BandlogException {
    public IllegalAuthenticationException() {
        super(ErrorCode.of("ILLEGAL_AUTHENTICATION"), "인증 과정에 오류가 발생했습니다.", "Illegal authentication error", 500);
    }
}
