package net.effize.bandlog.domain.user.model;

import net.effize.bandlog.domain.user.exception.EmailMalformedException;

public class Email {
    private final String value;

    private Email(String value) {
        if (!isValid(value)) {
            throw new EmailMalformedException();
        }
        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String fullEmail() {
        return value;
    }

    private boolean isValid(String email) {
        return email != null &&
                email.contains("@") &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
