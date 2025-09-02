package net.effize.bandlog.user.application.exception;

import net.effize.bandlog.shared.kernel.exception.BandlogException;
import net.effize.bandlog.shared.kernel.exception.ErrorCode;

public class UserNotSignedUpException extends BandlogException {
    public UserNotSignedUpException() {
        super(ErrorCode.of("NOT_SIGNED_UP"), "Bandlog에 계정을 생성해 주세요.", "User is not signed up yet", 401);
    }
}