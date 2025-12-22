package net.effize.bandlog.shared.auth;


public record AuthUser(
        Long id,
        String supabaseUserId,
        String email,
        String nickname
) {
}