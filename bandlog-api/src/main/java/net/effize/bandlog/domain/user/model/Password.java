package net.effize.bandlog.domain.user.model;

import net.effize.bandlog.domain.user.exception.PasswordMalformedException;
import net.effize.bandlog.domain.user.exception.PasswordTooShortException;
import net.effize.bandlog.domain.user.service.PasswordEncoder;

public class Password {

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password of(String rawPassword, PasswordEncoder encoder) {
        if (!isLengthValid(rawPassword)) {
            throw new PasswordTooShortException();
        }
        if (!isMatchingPattern(rawPassword)) {
            throw new PasswordMalformedException();
        }
        return new Password(encoder.encode(rawPassword));
    }

    private static boolean isLengthValid(String password) {
        return password.length() >= 8;
    }

    private static boolean isMatchingPattern(String password) {
        return password.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$"
        );
    }

    public String hashedPassword() {
        return value;
    }

    public boolean matches(String inputPassword, PasswordEncoder encoder) {
        return encoder.matches(inputPassword, this.value);
    }
}
