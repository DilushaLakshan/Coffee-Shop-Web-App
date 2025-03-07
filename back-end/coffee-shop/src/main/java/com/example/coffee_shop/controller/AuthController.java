package com.example.coffee_shop.controller;

import com.example.coffee_shop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String address = request.get("address");
        String contact = request.get("contact");
        String password = request.get("password");
        String userTypeIdStr = request.get("userTypeId");

        // Validate required fields
        if (name == null || email == null || address == null || contact == null || password == null || userTypeIdStr == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "All fields are required"));
        }

        // Validate userTypeId
        long userTypeId;
        try {
            userTypeId = Long.parseLong(userTypeIdStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid userTypeId"));
        }

        // Check if email is already taken
        if (userService.findByUsername(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already taken"));
        }

        // Register the user
        try {
            userService.registerUser(name, email, address, contact, password, userTypeId);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email"); // Use email as the username
        String password = request.get("password");

        // Validate required fields
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
        }

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password) // Use email as the username
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid email or password"));
        }
    }

    @GetMapping("/welcome")
    public ResponseEntity<Map<String, String>> welcome() {
        return ResponseEntity.ok(Map.of("message", "Welcome! You are authenticated."));
    }
}