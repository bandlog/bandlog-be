package net.effize.bandlog.team.adapter.out.user

import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.team.application.port.TeamUserInfo
import net.effize.bandlog.team.application.port.UserInfoPort
import net.effize.bandlog.user.api.UserQueryPort
import org.springframework.stereotype.Component

@Component
class UserInfoAdapter(
    private val userQueryPort: UserQueryPort
) : UserInfoPort {

    override fun findById(userId: UserId): TeamUserInfo? {
        return userQueryPort.findById(userId)?.let {
            TeamUserInfo(
                id = it.id,
                email = it.email,
                nickname = it.nickname
            )
        }
    }

    override fun findAllByIds(userIds: List<UserId>): List<TeamUserInfo> {
        return userQueryPort.findAllByIds(userIds).map {
            TeamUserInfo(
                id = it.id,
                email = it.email,
                nickname = it.nickname
            )
        }
    }
}
