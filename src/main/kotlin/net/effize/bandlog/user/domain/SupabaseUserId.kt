package net.effize.bandlog.user.domain

import jakarta.persistence.Embeddable

@Embeddable
data class SupabaseUserId(
    val value: String
)
