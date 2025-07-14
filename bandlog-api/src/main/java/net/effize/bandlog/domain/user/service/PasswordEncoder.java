package net.effize.bandlog.domain.user.service;

import net.effize.bandlog.domain.user.model.Password;

public interface PasswordEncoder {

    String encode(String rawPassword);

    boolean matches(String inputPassword, Password password);
}