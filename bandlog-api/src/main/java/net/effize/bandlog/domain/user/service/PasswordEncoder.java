package net.effize.bandlog.domain.user.service;

public interface PasswordEncoder {

    String encode(String rawPassword);

    boolean matches(String inputPassword, String hashedPassword);
}