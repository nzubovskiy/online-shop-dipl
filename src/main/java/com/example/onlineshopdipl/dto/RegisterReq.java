package com.example.onlineshopdipl.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public Role getRole() {
        return role;
    }
}
