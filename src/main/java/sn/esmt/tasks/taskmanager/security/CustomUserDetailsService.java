package sn.esmt.tasks.taskmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sn.esmt.tasks.taskmanager.entities.User;
import sn.esmt.tasks.taskmanager.exceptions.ResourceNotFoundException;
import sn.esmt.tasks.taskmanager.repositories.UserRepository;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        User user = userRepository.findByEmailOrUsername(username, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + username)
                );
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(String id) throws ResourceNotFoundException {
        // TODO Auto-generated method stub

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        return UserPrincipal.create(user);
    }

}
