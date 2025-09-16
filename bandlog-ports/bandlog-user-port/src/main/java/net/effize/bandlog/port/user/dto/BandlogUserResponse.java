package net.effize.bandlog.port.user.dto;

public record BandlogUserResponse(
        Long id,
        String supabaseUserId,
        String email,
        String nickname
) {
}
