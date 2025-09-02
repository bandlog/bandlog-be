package net.effize.bandlog.user.domain.model;

import jakarta.persistence.Embeddable;
import net.effize.bandlog.shared.kernel.domain.ValueObject;
import net.effize.bandlog.user.domain.exception.EmailMalformedException;

@Embeddable
public record Email(String value) implements ValueObject {
    
    public Email {
        if (!isValid(value)) {
            throw new EmailMalformedException();
        }
    }
    
    public static Email of(String value) {
        return new Email(value);
    }

    public String fullEmail() {
        return value;
    }

    private boolean isValid(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    @Override
    public String toString() {
        return value;
    }
}