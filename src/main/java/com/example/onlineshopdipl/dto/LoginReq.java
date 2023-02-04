package com.example.onlineshopdipl.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String password;
    private String username;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
