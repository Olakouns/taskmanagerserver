package sn.esmt.tasks.taskmanager.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.esmt.tasks.taskmanager.dto.converters.LoginRequest;
import sn.esmt.tasks.taskmanager.services.AuthService;

import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    @Transactional
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated @Valid LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }
}
