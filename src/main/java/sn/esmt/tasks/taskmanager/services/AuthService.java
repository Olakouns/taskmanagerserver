package sn.esmt.tasks.taskmanager.services;

import org.springframework.http.ResponseEntity;
import sn.esmt.tasks.taskmanager.dto.converters.LoginRequest;

public interface AuthService {
    ResponseEntity<?>  authenticateUser (LoginRequest loginRequest);
}
