package net.effize.bandlog.domain.user.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Nickname {
    private static final List<String> FIRST_NICKNAME_PARTS = List.of("소름돋는", "기가막힌", "천년에한번나올", "경악스러운", "온몸에전율이돋는", "짜릿한", "감동이흐르는", "참을수없는");
    private static final List<String> SECOND_NICKNAME_PARTS = List.of("재즈", "펑크", "메탈", "락", "팝", "인디");
    private static final List<String> THIRD_NICKNAME_PARTS = List.of("기타리스트", "보컬리스트", "드러머", "베이시스트", "키보디스트");


    private final String value;

    private Nickname(String value) {
        this.value = value;
    }

    public static Nickname of(String value) {
        return new Nickname(value);
    }

    public static Nickname randomNickname(Random random, Instant now) {
        String firstPart = FIRST_NICKNAME_PARTS.get(random.nextInt(FIRST_NICKNAME_PARTS.size()));
        String secondPart = SECOND_NICKNAME_PARTS.get(random.nextInt(SECOND_NICKNAME_PARTS.size()));
        String thirdPart = THIRD_NICKNAME_PARTS.get(random.nextInt(THIRD_NICKNAME_PARTS.size()));

        return new Nickname(firstPart + secondPart + thirdPart + now.toEpochMilli());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
