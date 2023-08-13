package sn.esmt.tasks.taskmanager.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sn.esmt.tasks.taskmanager.entities.Profile;
import sn.esmt.tasks.taskmanager.entities.User;
import sn.esmt.tasks.taskmanager.exceptions.ResourceNotFoundException;
import sn.esmt.tasks.taskmanager.repositories.ProfileRepository;
import sn.esmt.tasks.taskmanager.repositories.UserRepository;
import sn.esmt.tasks.taskmanager.security.UserPrincipal;

@Component
public class LoggerUser {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public LoggerUser(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }


    public Profile getCurrentProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) return null;
        User user = userRepository.getReferenceById(((UserPrincipal) auth.getPrincipal()).getId());
        return profileRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Profile", "current user", user.getId()));
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) return null;
        return userRepository.getReferenceById(((UserPrincipal) auth.getPrincipal()).getId());
    }

    public boolean checkAuthorization(String permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) return false;
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals(permission))
                return true;
        }
        return false;
    }
}
