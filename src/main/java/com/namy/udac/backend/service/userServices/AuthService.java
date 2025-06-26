package com.namy.udac.backend.service.userServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.userModel.Users;
import com.namy.udac.backend.repository.userRepo.UserRepository;

@Service
public class AuthService {
    
    //@Autowired
    //private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManger;
    
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
    
    public Users createUser(Users user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword); //Set the hashed password to the users object

        // Generate idUser based on role
        String role = user.getRole().toUpperCase();
        String prefix = role.equals("ADMIN") ? "admin" : "student";

        // Get count of existing users by role to generate new suffix
        int max = userRepository.findMaxIdUserByRole(role);
        String formattedCount = String.format("%03d", max + 1);

        String generatedId = prefix + formattedCount;
        user.setIdUser(generatedId);

        return userRepository.createUser(user);
    }

    public String login(Users user) {
        Authentication authentication = authManger.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername(), user.getRole());
        } 

        return "Login failed!";
    }

    public String requestPasswordReset (String username) {
        Users user = userRepository.getUserByUsername(username);

        if (user == null) {
            return "User not found!";
        }

        String userEmail = user.getEmail();
        String token = jwtService.generateResetPasswordToken(username, 15 * 60 * 1000);
        sendResetPasswordEmail(token, userEmail);
        return "Password reset link: http://localhost:8080/auth/reset-password?token=" + token;
    // return "Password reset link sent to " + userEmail;
    }

    public String sendResetPasswordEmail(String token, String email){
        /*String resetLink = "http://localhost:3000/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the following link to reset your password: " + resetLink 
                        + "\n\nThis link will expire in 15 minutes.");

        mailSender.send(message);*/

        return "Password reset link sent to " + email;
    }
    public String resetPassword(String token, String newPassword) {
        String username;
        try {
            username = jwtService.extractUserName(token); 
        } catch (Exception e) {
            return "Invalid or expired token.";
        }

        Users user = userRepository.getUserByUsername(username);
        
        if (user == null) {
            return "User not found!";
        }

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword); 
        return userRepository.updatePassword(user.getIdUser(), user.getPassword());
    }

    public Users getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

}
