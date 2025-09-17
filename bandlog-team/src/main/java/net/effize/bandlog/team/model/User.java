package net.effize.bandlog.team.model;

public class User {
    private final UserId userId;
    private final String email;
    private final String nickname;

    public User(UserId userId, String email, String nickname) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }

    public UserId userId() {
        return userId;
    }

    public String email() {
        return email;
    }

    public String nickname() {
        return nickname;
    }
}
