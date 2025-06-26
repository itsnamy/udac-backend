package com.namy.udac.backend.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.userModel.Users;
import com.namy.udac.backend.service.userServices.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    Users createUser(@RequestBody Users newUser){
        System.out.println("Creating user: " + newUser.getUsername());
        return authService.createUser(newUser);
    };

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        // Call the login service method to authenticate the user and generate token
        String token = authService.login(user);

        // If the token is "Login failed!", respond with an error
        if (token.equals("Login failed!")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        user = authService.getUserByUsername(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("idUser", user.getIdUser());
        response.put("role", user.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public String requestPasswordReset(@RequestBody Users user) {
        return authService.requestPasswordReset(user.getUsername());
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        return authService.resetPassword(token, newPassword);
    }
}
