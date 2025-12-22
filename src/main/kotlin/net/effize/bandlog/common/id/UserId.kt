package net.effize.bandlog.common.id

@JvmInline
value class UserId(val value: Long) {
    init {
        require(value > 0) { "UserId must be positive" }
    }
}
