package net.effize.bandlog.user.dto.response;

public record UserResponse(
        Long id,
        String supabaseUserId,
        String email,
        String nickname
) {
}
