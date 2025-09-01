package net.effize.bandlog.auth.application.dto;

public record AuthenticationPrincipal(String supabaseUserId, String email) {
}