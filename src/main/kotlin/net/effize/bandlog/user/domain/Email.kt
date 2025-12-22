package net.effize.bandlog.user.domain

import jakarta.persistence.Embeddable
import net.effize.bandlog.user.domain.exception.EmailMalformedException

@Embeddable
data class Email(
    val value: String
) {
    init {
        require(isValid(value)) { throw EmailMalformedException() }
    }

    fun fullEmail(): String = value

    private fun isValid(email: String?): Boolean {
        return email != null &&
                email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }
}
