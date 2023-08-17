package sn.esmt.tasks.taskmanager.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.dto.converters.JwtAuthenticationResponse;
import sn.esmt.tasks.taskmanager.dto.converters.LoginRequest;
import sn.esmt.tasks.taskmanager.dto.converters.RegisterUser;
import sn.esmt.tasks.taskmanager.entities.Profile;
import sn.esmt.tasks.taskmanager.entities.Role;
import sn.esmt.tasks.taskmanager.entities.User;
import sn.esmt.tasks.taskmanager.entities.enums.EmailType;
import sn.esmt.tasks.taskmanager.entities.enums.StatusUser;
import sn.esmt.tasks.taskmanager.exceptions.RequestNotAcceptableException;
import sn.esmt.tasks.taskmanager.exceptions.ResourceNotFoundException;
import sn.esmt.tasks.taskmanager.repositories.ProfileRepository;
import sn.esmt.tasks.taskmanager.repositories.RoleRepository;
import sn.esmt.tasks.taskmanager.repositories.UserRepository;
import sn.esmt.tasks.taskmanager.security.TokenAuthentificationProvider;
import sn.esmt.tasks.taskmanager.security.UserPrincipal;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenAuthentificationProvider tokenProvider;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, TokenAuthentificationProvider tokenProvider, ProfileRepository profileRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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
        if (user.isActive() && user.getStatus() == StatusUser.ACTIVE) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            JwtAuthenticationResponse token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(token);
        } else if (user.getStatus() == StatusUser.ACTIVATION_PENDING) {
            return ResponseEntity.ok(new ApiResponse(false, "Le compte est en attente de confirmation."));
        } else {
            return ResponseEntity.ok(new ApiResponse(true, loginRequest.getUsername()));
        }
    }

    @Override
    public ApiResponse createUser(RegisterUser registerUser) {
        Role role = roleRepository.findByName("USER");

        User user = new User();
        user.setActive(false);
        user.setEmail(registerUser.getEmail());
        user.setName(registerUser.getUsername());
        user.setUsername(registerUser.getUsername());
        user.setStatus(StatusUser.ACTIVATION_PENDING);
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setRoles(Collections.singletonList(role));
        user = userRepository.save(user);

        Profile profile = new Profile();
        profile.setEmail(registerUser.getEmail());
        profile.setFirstName(registerUser.getFirstName());
        profile.setLastName(registerUser.getLastName());
        profile.setUser(user);
        profile.setUsername(registerUser.getUsername());
        profileRepository.save(profile);

        String token = this.tokenProvider.generateSimpleToken(user.getId());
        try {
            this.emailService.senEmail(EmailType.ACTIVATE_ACCOUNT, registerUser.getEmail(), token, registerUser.getFirstName() + ' ' + registerUser.getLastName());
            return new ApiResponse(true, "A mail is send in you mail box");
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponse confirmAccount(String token) {
        if (!this.tokenProvider.validateToken(token)) {
            throw new RequestNotAcceptableException("Invalid token");
        }

        String id = this.tokenProvider.getUserIdFromJWT(token);
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        if (user.isActive()) {
            return new ApiResponse(false, "The user's account has already been confirmed");
        }
        user.setActive(true);
        user.setStatus(StatusUser.ACTIVE);
        userRepository.save(user);
        return new ApiResponse(true, "The user's account has been confirmed");
    }
}
