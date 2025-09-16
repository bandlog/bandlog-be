package net.effize.bandlog.adapter.user.dto;

public record BandlogUserResponse(
        Long id,
        String supabaseUserId,
        String email,
        String nickname
) {
}
