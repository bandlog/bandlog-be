package net.effize.bandlog.domain.user.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserIdTest {
    @Test
    void 같은_값을_가진_UserId는_동일해야_한다() {
        // arrange
        UserId id1 = new UserId(2L);
        UserId id2 = new UserId(2L);

        // act & assert
        assertThat(id1).isEqualTo(id2);
    }

    @Test
    void 다른_값을_가진_UserId는_동일하지_않아야_한다() {
        // arrange
        UserId id1 = new UserId(2L);
        UserId id2 = new UserId(5L);

        // act & assert
        assertThat(id1).isNotEqualTo(id2);
    }
}