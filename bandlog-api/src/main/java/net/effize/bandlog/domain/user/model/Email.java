package net.effize.bandlog.domain.user.model;

public class Email {
    private Email(String value) {
        if (!isValid(value)) {
            throw new RuntimeException("Invalid email address");
        }
        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    private final String value;

    public String fullEmail() {
        return value;
    }

    private boolean isValid(String email) {
        return email != null &&
                email.contains("@") &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
