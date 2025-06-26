package com.namy.udac.backend.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.userModel.Users;
import com.namy.udac.backend.repository.userRepo.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard";
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/student/dashboard")
    public String studentDashboard() {
        return "student_dashboard";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin/getAllUser")
    List<Users> getAllUsers(){
        return userRepository.getAllUsers();
    }

    @GetMapping("common/getUserbyId")
    ResponseEntity<?> getUserbyId(@RequestParam String idUser){
        Users user = userRepository.getUserById(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("message", "User not found"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("idUser", user.getIdUser());
        response.put("role", user.getRole());
        response.put("fullname", user.getFullname());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());

        return ResponseEntity.ok(response);
    }

    @PutMapping("common/updateUser")
    ResponseEntity<?> updateUser(@RequestBody Users updatedUser){
        // Get the existing user data from the database
        Users existingUser = userRepository.getUserById(updatedUser.getIdUser());

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("message", "User not found"));
        }

        // Update the selected existing user data with the new data (to prevent overwriting on the sesitive data)
        existingUser.setFullname(updatedUser.getFullname());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());

        // Update the user in the database
        Users user = userRepository.updateUser(existingUser);
        Map<String, Object> response = new HashMap<>();
        response.put("idUser", user.getIdUser());
        response.put("role", user.getRole());
        response.put("fullname", user.getFullname());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("common/deleteUser")
    int deleteUser(@PathVariable String idUser){
        return userRepository.deleteUser(idUser);
    }
}
