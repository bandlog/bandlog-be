package net.effize.bandlog.domain.user.model;

import jakarta.persistence.Embeddable;
import net.effize.bandlog.domain.user.exception.EmailMalformedException;

import java.util.Objects;

@Embeddable
public class Email {
    private String value;

    protected Email() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
