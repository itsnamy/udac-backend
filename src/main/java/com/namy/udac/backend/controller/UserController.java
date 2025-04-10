package com.namy.udac.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.User;
import com.namy.udac.backend.repository.UserRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")
    int newUser(@RequestBody User newUser){
        System.out.println("Creating user: " + newUser.getUsername());
        return userRepository.createUser(newUser);
    };
    
    @GetMapping("/getAllUser")
    List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }
}
