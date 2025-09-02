package net.effize.bandlog.team.integration;

import net.effize.bandlog.team.application.TeamApplicationService;
import net.effize.bandlog.shared.kernel.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Handles events from the User bounded context.
 * This is an example of cross-context integration using domain events.
 */
@Component
public class UserEventHandler {
    
    private static final Logger log = LoggerFactory.getLogger(UserEventHandler.class);
    
    private final TeamApplicationService teamApplicationService;
    
    public UserEventHandler(TeamApplicationService teamApplicationService) {
        this.teamApplicationService = teamApplicationService;
    }
    
    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("Handling UserRegistered event for user: {}", event.userId());
        
        // Create a personal team for the new user
        String teamName = event.nickname() + "'s Team";
        String description = "Personal team for " + event.nickname();
        
        teamApplicationService.createTeam(teamName, description, event.userId());
        
        log.info("Created personal team for user: {}", event.userId());
    }
}