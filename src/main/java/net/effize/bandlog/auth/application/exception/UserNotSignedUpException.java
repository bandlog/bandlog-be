package net.effize.bandlog.auth.application.exception;

import net.effize.bandlog.common.exception.BandlogException;
import net.effize.bandlog.common.exception.ErrorCode;

public class UserNotSignedUpException extends BandlogException {
    public UserNotSignedUpException() {
        super(ErrorCode.of("NOT_SIGNED_UP"), "Bandlog에 계정을 생성해 주세요.", "User is not signed up yet", 401);
    }
}
