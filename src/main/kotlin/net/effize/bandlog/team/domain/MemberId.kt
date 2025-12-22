package net.effize.bandlog.team.domain

@JvmInline
value class MemberId(val value: Long) {
    companion object {
        fun of(value: Long): MemberId = MemberId(value)
    }
}
