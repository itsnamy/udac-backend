package com.namy.udac.backend.repository.userRepo;

import java.util.List;

import com.namy.udac.backend.model.userModel.Users;

public interface UserRepository {
    Users createUser(Users user);
    int findMaxIdUserByRole(String role);
    Users getUserById(String idUser);
    Users getUserByUsername(String username);
    List<Users> getAllUsers();
    Users updateUser(Users user);
    String updatePassword(String idUser, String password);
    int deleteUser(String idUser);
    
}
