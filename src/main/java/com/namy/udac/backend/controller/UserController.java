package com.namy.udac.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    Users getUserbyId(@RequestParam String idUser){
        return userRepository.getUserById(idUser);
    }

    @PutMapping("common/updateUser")
    Users updateUser(@RequestBody Users user){
        return userRepository.updateUser(user);
    }

    @DeleteMapping("common/deleteUser")
    int deleteUser(@PathVariable String idUser){
        return userRepository.deleteUser(idUser);
    }
}
