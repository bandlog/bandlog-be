package net.effize.bandlog.application.auth.dto;

import net.effize.bandlog.domain.user.model.Email;
import net.effize.bandlog.domain.user.model.SupabaseUserId;

public record AuthenticationPrincipal(SupabaseUserId supabaseUserId, Email email) {
}