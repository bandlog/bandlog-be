package net.effize.bandlog.port.user;

import net.effize.bandlog.port.user.dto.BandlogUserResponse;

import java.util.List;

public interface BandlogUserPort {
    List<BandlogUserResponse> findAllByIdIn(List<Long> ids);
}
