package sn.esmt.tasks.taskmanager.services;

import org.springframework.http.ResponseEntity;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.dto.converters.LoginRequest;
import sn.esmt.tasks.taskmanager.dto.converters.RegisterUser;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ApiResponse createUser(RegisterUser registerUser);

    ApiResponse confirmAccount(String token);

}
