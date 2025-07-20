package net.effize.bandlog.domain.auth.model;

import net.effize.bandlog.domain.user.model.Email;
import net.effize.bandlog.domain.user.model.SupabaseUserId;

public class AuthenticationPrincipal {

    private final SupabaseUserId supabaseUserId;
    private final Email email;

    public AuthenticationPrincipal(SupabaseUserId supabaseUserId, Email email) {
        this.supabaseUserId = supabaseUserId;
        this.email = email;
    }

    public SupabaseUserId getSupabaseUserId() {
        return supabaseUserId;
    }

    public Email getEmail() {
        return email;
    }
}