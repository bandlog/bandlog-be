package net.effize.bandlog.common.exception

@JvmInline
value class ErrorCode private constructor(val value: String) {
    companion object {
        val BAD_REQUEST = ErrorCode("BAD_REQUEST")
        val INTERNAL_SERVER_ERROR = ErrorCode("INTERNAL_SERVER_ERROR")

        fun of(value: String): ErrorCode = ErrorCode(value)
    }

    override fun toString(): String = value
}
