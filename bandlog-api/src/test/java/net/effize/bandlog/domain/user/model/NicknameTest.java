package net.effize.bandlog.domain.user.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class NicknameTest {

    @Test
    public void randomNickname은_null이_아닌_Nickname_객체를_반환해야_한다() {
        // arrange
        Random random = new Random();
        Instant now = Instant.now();

        // act
        Nickname nickname = Nickname.randomNickname(random, now);

        // assert
        assertThat(nickname).isNotNull();
    }

    @Test
    public void randomNickname으로_생성된_닉네임은_빈_문자열이_아니어야_한다() {
        // arrange
        Random random = new Random();
        Instant now = Instant.now();

        // act
        Nickname nickname = Nickname.randomNickname(random, now);

        // assert
        assertThat(nickname.stringValue()).isNotEmpty();
    }

    @Test
    public void randomNickname으로_생성된_닉네임은_정해진_패턴을_따라야_한다() {
        // arrange
        Random random = new Random();
        Instant now = Instant.ofEpochMilli(1234567890L);

        // act
        Nickname nickname = Nickname.randomNickname(random, now);
        String nicknameValue = nickname.stringValue();

        // assert
        assertThat(nicknameValue).matches("(소름돋는|기가막힌|천년에한번나올|경악스러운|온몸에전율이돋는|짜릿한|감동이흐르는|참을수없는)(재즈|펑크|메탈|락|팝|인디)(기타리스트|보컬리스트|드러머|베이시스트|키보디스트)\\d+");
    }

    @Test
    public void 동일한_Random_시드와_시간으로_생성된_닉네임은_같아야_한다() {
        // arrange
        Random random1 = new Random(123);
        Random random2 = new Random(123);
        Instant now = Instant.ofEpochMilli(1234567890L);

        // act
        Nickname nickname1 = Nickname.randomNickname(random1, now);
        Nickname nickname2 = Nickname.randomNickname(random2, now);

        // assert
        assertThat(nickname1.stringValue()).isEqualTo(nickname2.stringValue());
    }

    @Test
    public void 다른_시간으로_생성된_닉네임은_달라야_한다() {
        // arrange
        Random random1 = new Random(123);
        Random random2 = new Random(123);
        Instant time1 = Instant.ofEpochMilli(1234567890L);
        Instant time2 = Instant.ofEpochMilli(1234567891L);

        // act
        Nickname nickname1 = Nickname.randomNickname(random1, time1);
        Nickname nickname2 = Nickname.randomNickname(random2, time2);

        // assert
        assertThat(nickname1.stringValue()).isNotEqualTo(nickname2.stringValue());
    }

    @Test
    public void 여러_번_호출하면_다양한_닉네임이_생성된다() {
        // arrange
        Random random = new Random();

        // act
        boolean foundDifferent = false;
        String firstNickname = Nickname.randomNickname(random, Instant.now()).stringValue();

        for (int i = 0; i < 10; i++) {
            String currentNickname = Nickname.randomNickname(random, Instant.now()).stringValue();
            if (!firstNickname.equals(currentNickname)) {
                foundDifferent = true;
                break;
            }
        }

        // assert
        assertThat(foundDifferent).isTrue();
    }
}
