package com.namy.udac.backend.repository;

import java.util.List;
import com.namy.udac.backend.model.User;

public interface UserRepository {
    int createUser(User user);
    User getUserById(String idUser);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    int updateUser(User user);
    int deleteUser(String idUser);
    
}
