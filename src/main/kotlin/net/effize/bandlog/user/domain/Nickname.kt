package net.effize.bandlog.user.domain

import jakarta.persistence.Embeddable
import java.time.Instant
import java.util.Random

@Embeddable
data class Nickname(
    val value: String
) {
    companion object {
        private val FIRST_NICKNAME_PARTS = listOf("소름돋는", "기가막힌", "천년에한번나올", "경악스러운", "온몸에전율이돋는", "짜릿한", "감동이흐르는", "참을수없는")
        private val SECOND_NICKNAME_PARTS = listOf("재즈", "펑크", "메탈", "락", "팝", "인디")
        private val THIRD_NICKNAME_PARTS = listOf("기타리스트", "보컬리스트", "드러머", "베이시스트", "키보디스트")

        fun randomNickname(random: Random, now: Instant): Nickname {
            val firstPart = FIRST_NICKNAME_PARTS[random.nextInt(FIRST_NICKNAME_PARTS.size)]
            val secondPart = SECOND_NICKNAME_PARTS[random.nextInt(SECOND_NICKNAME_PARTS.size)]
            val thirdPart = THIRD_NICKNAME_PARTS[random.nextInt(THIRD_NICKNAME_PARTS.size)]

            return Nickname(firstPart + secondPart + thirdPart + now.toEpochMilli())
        }
    }

    fun stringValue(): String = value
}
