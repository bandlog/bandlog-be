package net.effize.bandlog.user.application.dto;

import net.effize.bandlog.user.domain.model.Email;
import net.effize.bandlog.user.domain.model.SupabaseUserId;

public record AuthenticationPrincipal(SupabaseUserId supabaseUserId, Email email) {
}