package net.effize.bandlog.port.user;

import net.effize.bandlog.port.user.dto.BandlogUserResponse;

import java.util.Optional;

public interface BandlogAuthUserPort {
    void signUp(String supabaseUserId, String email);

    Optional<BandlogUserResponse> findBySupabaseUserId(String supabaseUserId);
}
