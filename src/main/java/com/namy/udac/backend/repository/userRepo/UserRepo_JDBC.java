package com.namy.udac.backend.repository.userRepo;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.userModel.Users;

@Repository
public class UserRepo_JDBC implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepo_JDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Users createUser(Users user) {
        String sql = "INSERT INTO users (idUser, role, fullname, username, password, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, 
                        user.getIdUser(), 
                        user.getRole(), 
                        user.getFullname(), 
                        user.getUsername(), 
                        user.getPassword(), 
                        user.getEmail(), 
                        user.getPhone()
        ); 
        // rowsAffected means number of rows affected by the insert operation
        return rowsAffected > 0 ? user : null; // Return the user if the insert was successful, otherwise return null
    }

    @Override
    public int findMaxIdUserByRole(String role) {
        String prefix = role.toLowerCase(); // e.g., "admin" or "student"
        String sql = "SELECT MAX(CAST(SUBSTRING(idUser, LENGTH(?) + 1) AS UNSIGNED)) FROM users WHERE role = ?";
    
        Integer max = jdbcTemplate.queryForObject(sql,Integer.class, prefix, role);
        return max != null ? max : 0;
    }

    @Override
    public Users getUserById(String idUser) {
        String sql = "SELECT * FROM users WHERE idUser = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, idUser);
    }

    @Override
    public Users getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, username);
    }

    @Override
    public List<Users> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Users updateUser(Users user) {
        String sql = "UPDATE users SET role = ?, fullname = ?, username = ?, password = ?, email = ?, phone = ? WHERE idUser = ?";
        int rowsAffected = jdbcTemplate.update(
                    sql, user.getRole(), 
                    user.getFullname(), 
                    user.getUsername(), 
                    user.getPassword(), 
                    user.getEmail(), 
                    user.getPhone(), 
                    user.getIdUser());
        // rowsAffected means number of rows affected by the insert operation
        return rowsAffected > 0 ? user : null; // Return the user if the insert was successful, otherwise return null
    }

    @Override
    public String updatePassword(String idUser, String password) {
        String sql = "UPDATE users SET password = ? WHERE idUser = ?";
        int rowsAffected = jdbcTemplate.update(sql, password, idUser);
        return rowsAffected > 0 ? "Password updated successfully" : "Failed to update password";
    }
    
    @Override
    public int deleteUser(String idUser) {
        String sql = "DELETE FROM users WHERE idUser = ?";
        return jdbcTemplate.update(sql, idUser);
        
    }

    private final RowMapper<Users> userRowMapper = (rs, rowNum) -> new Users(
        rs.getString("idUser"),
        rs.getString("role"),
        rs.getString("fullname"),
        rs.getString("username"),
        rs.getString("password"),
        rs.getString("email"),
        rs.getString("phone")
    );
}

