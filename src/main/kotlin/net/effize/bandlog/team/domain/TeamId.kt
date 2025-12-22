package net.effize.bandlog.team.domain

@JvmInline
value class TeamId(val value: Long) {
    companion object {
        fun of(value: Long): TeamId = TeamId(value)
    }
}
