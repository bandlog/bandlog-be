package net.effize.bandlog.user.application;

import net.effize.bandlog.shared.kernel.types.UserId;
import net.effize.bandlog.user.application.dto.AuthUser;
import net.effize.bandlog.user.application.dto.AuthenticationPrincipal;
import net.effize.bandlog.user.application.exception.IllegalAuthenticationException;
import net.effize.bandlog.user.application.exception.UserNotSignedUpException;
import net.effize.bandlog.user.domain.model.User;
import net.effize.bandlog.user.domain.repository.UserRepository;
import net.effize.bandlog.user.adapters.web.dto.UserProfileResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

@Service
public class UserApplicationService {
    private static final Random RANDOM = new SecureRandom();
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserApplicationService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void signup(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authenticationPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        if (userRepository.existsBySupabaseUserId(authenticationPrincipal.supabaseUserId())) {
            return;
        }

        User newUser = User.create(
                authenticationPrincipal.supabaseUserId(),
                authenticationPrincipal.email(),
                Instant.now(),
                RANDOM
        );

        User savedUser = userRepository.save(newUser);
        
        // Update the UserId in the event after save (ID is assigned)
        savedUser.getEvents().forEach(event -> {
            if (event instanceof net.effize.bandlog.shared.kernel.event.UserRegisteredEvent userRegisteredEvent) {
                net.effize.bandlog.shared.kernel.event.UserRegisteredEvent updatedEvent = 
                    new net.effize.bandlog.shared.kernel.event.UserRegisteredEvent(
                        savedUser.id(),
                        userRegisteredEvent.email(),
                        userRegisteredEvent.nickname(),
                        userRegisteredEvent.occurredOn()
                    );
                eventPublisher.publishEvent(updatedEvent);
            } else {
                eventPublisher.publishEvent(event);
            }
        });
        savedUser.clearEvents();
    }

    @Transactional(readOnly = true)
    public AuthUser authenticate(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAuthenticationException();
        }

        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        User user = userRepository.findBySupabaseUserId(authPrincipal.supabaseUserId())
                .orElseThrow(() -> new UserNotSignedUpException());

        return new AuthUser(user);
    }
    
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(UserId userId) {
        User user = userRepository.findById(userId.value())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
                
        return new UserProfileResponse(
            user.id().value(),
            user.email().value(),
            user.nickname().value(),
            user.createdAt()
        );
    }
}