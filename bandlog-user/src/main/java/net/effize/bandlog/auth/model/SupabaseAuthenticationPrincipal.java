package net.effize.bandlog.auth.model;


public record SupabaseAuthenticationPrincipal(String supabaseUserId, String email) {
}