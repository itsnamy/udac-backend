package com.namy.udac.backend.model.userModel;

public class Users {
    private String idUser;
    private String role;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phone;

    public Users() {}

    public Users(Users user) {
        this.idUser = user.idUser;
        this.role = user.role;
        this.fullname = user.fullname;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.phone = user.phone;
    }
    
    public Users(String idUser, String role, String fullname, String username, String password, String email, String phone) {
        this.idUser = idUser;
        this.role = role;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Users{" +
                "idUser='" + idUser + '\'' +
                ", role='" + role + '\'' +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
