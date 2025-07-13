package net.effize.bandlog.domain.user.exception;

import net.effize.bandlog.domain.common.exception.BandlogException;
import net.effize.bandlog.domain.common.exception.ErrorCode;

public class PasswordMalformedException extends BandlogException {
    public PasswordMalformedException() {
        super(ErrorCode.of("PASSWORD_MALFORMED_ERROR"), "비밀번호가 영문대소문자, 숫자, 특수문자를 포함하는지 확인해주세요.", "password is malformed", 400);
    }
}
