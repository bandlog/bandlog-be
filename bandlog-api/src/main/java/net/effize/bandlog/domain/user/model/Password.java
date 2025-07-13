package net.effize.bandlog.domain.user.model;

public class Password {
    private String value;

    private Password(String value) {
        if (!isValid(value)) {
            throw new RuntimeException("Invalid password");
        }
        this.value = value;
    }

    public static Password of(String value) {
        return new Password(value);
    }

    private boolean isValid(String password) {
        return password.length() >= 8 &&
                password.matches(
                        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$"
                );
    }
}
