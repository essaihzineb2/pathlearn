package org.example.projet.controller;

import org.example.projet.dto.JwtResponse;
import org.example.projet.dto.LoginRequest;
import org.example.projet.dto.MessageResponse;
import org.example.projet.dto.SignupRequest;
import org.example.projet.model.auth.User;
import org.example.projet.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody SignupRequest signupRequest) {
        String message = authService.register(signupRequest);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
}
