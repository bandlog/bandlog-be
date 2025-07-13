package net.effize.bandlog.domain.user.model;

import net.effize.bandlog.domain.user.exception.EmailMalformedException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmailTest {
    @Test
    public void 올바른_이메일은_정상적으로_생성되어야_한다() {
        // arrange
        String input = "test@example.com";

        // act
        Email email = Email.of(input);

        // assert
        assertThat(email).isNotNull();
    }

    @Test
    public void 포맷을_만족하지_않는_이메일은_생성_실패해야_한다() {
        // arrange
        String input = "test";

        // act & assert
        assertThatThrownBy(() -> {
            Email email = Email.of(input);
        }).isInstanceOf(EmailMalformedException.class);
    }
}
