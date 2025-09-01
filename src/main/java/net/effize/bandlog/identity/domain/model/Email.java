package net.effize.bandlog.identity.domain.model;

import jakarta.persistence.Embeddable;
import net.effize.bandlog.identity.domain.exception.EmailMalformedException;

@Embeddable
public record Email(
        String value
) {
    public Email {
        if (!isValid(value)) {
            throw new EmailMalformedException();
        }
    }

    public String fullEmail() {
        return value;
    }

    private boolean isValid(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
