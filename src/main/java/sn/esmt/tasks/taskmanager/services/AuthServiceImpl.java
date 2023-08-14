package sn.esmt.tasks.taskmanager.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.dto.converters.JwtAuthenticationResponse;
import sn.esmt.tasks.taskmanager.dto.converters.LoginRequest;
import sn.esmt.tasks.taskmanager.entities.User;
import sn.esmt.tasks.taskmanager.entities.enums.StatusUser;
import sn.esmt.tasks.taskmanager.repositories.UserRepository;
import sn.esmt.tasks.taskmanager.security.TokenAuthentificationProvider;
import sn.esmt.tasks.taskmanager.security.UserPrincipal;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenAuthentificationProvider tokenProvider;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, TokenAuthentificationProvider tokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserPrincipal userP = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userP.getId()).orElseThrow();
        if (user.isActive()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            JwtAuthenticationResponse token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(token);
        } else if (user.getStatus() == StatusUser.ACTIVATION_PENDING) {
            return ResponseEntity.ok(new ApiResponse(true, loginRequest.getUsername()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Le compte est en attente de confirmation."));
        }

    }
}
