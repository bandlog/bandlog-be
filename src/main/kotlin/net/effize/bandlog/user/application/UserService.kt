package net.effize.bandlog.user.application

import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.user.adapter.out.persistence.UserJpaEntity
import net.effize.bandlog.user.adapter.out.persistence.UserJpaRepository
import net.effize.bandlog.user.api.UserInfo
import net.effize.bandlog.user.api.UserQueryPort
import net.effize.bandlog.user.domain.Email
import net.effize.bandlog.user.domain.SupabaseUserId
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant

@Service
class UserService(
    private val userRepository: UserJpaRepository
) : UserQueryPort {

    companion object {
        private val RANDOM = SecureRandom()
    }

    override fun signUp(supabaseUserId: String, email: String) {
        val supabaseId = SupabaseUserId(supabaseUserId)
        if (userRepository.existsBySupabaseUserId(supabaseId)) {
            return
        }

        val newUser = UserJpaEntity.create(
            supabaseUserId = supabaseId,
            email = Email(email),
            now = Instant.now(),
            random = RANDOM
        )

        userRepository.save(newUser)
    }

    override fun findBySupabaseUserId(supabaseUserId: String): UserInfo? {
        return userRepository.findBySupabaseUserId(SupabaseUserId(supabaseUserId))?.toUserInfo()
    }

    override fun findById(userId: UserId): UserInfo? {
        return userRepository.findById(userId.value).orElse(null)?.toUserInfo()
    }

    override fun findAllByIds(userIds: List<UserId>): List<UserInfo> {
        return userRepository.findAllByIdIn(userIds.map { it.value })
            .map { it.toUserInfo() }
    }

    private fun UserJpaEntity.toUserInfo(): UserInfo {
        return UserInfo(
            id = this.id(),
            supabaseUserId = this.supabaseUserId.value,
            email = this.email.value,
            nickname = this.nickname.value
        )
    }
}
