package net.effize.bandlog.identity.domain.exception;

import net.effize.bandlog.common.api.BandlogException;
import net.effize.bandlog.common.api.ErrorCode;

public class EmailMalformedException extends BandlogException {
    public EmailMalformedException() {
        super(ErrorCode.of("EMAIL_MALFORMED_ERROR"), "이메일 형식이 잘못되었습니다.", "email malformed", 400);
    }
}
