package net.effize.bandlog.domain.user.model;

import net.effize.bandlog.domain.user.exception.PasswordMalformedException;
import net.effize.bandlog.domain.user.exception.PasswordTooShortException;

public class RawPassword {
    private String value;

    private RawPassword(String value) {
        if (!isLengthValid(value)) {
            throw new PasswordTooShortException();
        }
        if (!isMatchingPattern(value)) {
            throw new PasswordMalformedException();
        }
        this.value = value;
    }

    public static RawPassword of(String value) {
        return new RawPassword(value);
    }

    private boolean isLengthValid(String password) {
        return password.length() >= 8;
    }

    private boolean isMatchingPattern(String password) {
        return password.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$"
        );
    }
}
