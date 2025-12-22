package net.effize.bandlog.auth.domain

data class SupabaseAuthenticationPrincipal(
    val supabaseUserId: String,
    val email: String
)
