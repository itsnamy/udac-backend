package com.namy.udac.backend.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.namy.udac.backend.model.User;

@Repository
public class UserRepo_JDBC implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepo_JDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createUser(User user) {
        String sql = "INSERT INTO users (idUser, role, fullname, username, password, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, user.getIdUser(), user.getRole(), user.getFullname(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone());
    }

    @Override
    public User getUserById(String idUser) {
        String sql = "SELECT * FROM users WHERE idUser = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, idUser);
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, username);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public int updateUser(User user) {
        String sql = "UPDATE users SET role = ?, fullname = ?, username = ?, password = ?, email = ?, phone = ? WHERE idUser = ?";
        return jdbcTemplate.update(sql, user.getRole(), user.getFullname(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getIdUser());
    }

    @Override
    public int deleteUser(String idUser) {
        String sql = "DELETE FROM users WHERE idUser = ?";
        return jdbcTemplate.update(sql, idUser);
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
        rs.getString("idUser"),
        rs.getString("role"),
        rs.getString("fullname"),
        rs.getString("username"),
        rs.getString("password"),
        rs.getString("email"),
        rs.getString("phone")
    );
}

