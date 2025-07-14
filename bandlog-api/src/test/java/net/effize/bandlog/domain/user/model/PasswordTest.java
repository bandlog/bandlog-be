package net.effize.bandlog.domain.user.model;

import net.effize.bandlog.domain.user.exception.PasswordMalformedException;
import net.effize.bandlog.domain.user.exception.PasswordTooShortException;
import net.effize.bandlog.domain.user.service.PasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {
    private final PasswordEncoder encoder = new TestPasswordEncoder();

    @Test
    public void 비밀번호는_8자_이상_그리고_영대소문자_숫자_특수문자를_포함하면_생성되어야_한다() {
        // arrange
        String input = "Test123!";

        // act
        Password password = Password.of(input, encoder);

        // assert
        assertThat(password).isNotNull();
    }

    @Test
    public void 비밀번호는_8자_미만이면_생성_실패해야_한다() {
        // arrange
        String input = "Test123";

        // act & assert
        assertThatThrownBy(() -> {
            Password password = Password.of(input, encoder);
        }).isInstanceOf(PasswordTooShortException.class);
    }

    @Test
    public void 비밀번호는_영문대소문자_숫자_특수문자를_1개_이상_포함하지_못하면_생성_실패해야_한다() {
        // arrange
        String input = "Test1234";

        // act & assert
        assertThatThrownBy(() -> {
            Password password = Password.of(input, encoder);
        }).isInstanceOf(PasswordMalformedException.class);
    }

    static class TestPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(String rawPassword) {
            return rawPassword;
        }

        @Override
        public boolean matches(String inputPassword, Password password) {
            return false;
        }
    }
}
