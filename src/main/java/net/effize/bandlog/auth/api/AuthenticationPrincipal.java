package net.effize.bandlog.auth.api;

public record AuthenticationPrincipal(String supabaseUserId, String email) {
}