package net.effize.bandlog.shared.auth;


public record SupabaseAuthenticationPrincipal(String supabaseUserId, String email) {
}