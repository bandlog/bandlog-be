package net.effize.bandlog.domain.user.exception;

import net.effize.bandlog.domain.common.exception.BandlogException;
import net.effize.bandlog.domain.common.exception.ErrorCode;

public class PasswordTooShortException extends BandlogException {
    public PasswordTooShortException() {
        super(ErrorCode.of("PASSWORD_TOO_SHORT_ERROR"), "비밀번호가 8자 미만입니다.", "password is too short", 400);
    }
}
