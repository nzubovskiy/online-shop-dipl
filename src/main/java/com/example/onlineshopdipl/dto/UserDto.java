package com.example.onlineshopdipl.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String email;
    private String firstName;
    private Integer id;
    private String lastName;
    private String phone;
    private LocalDateTime regDate;
    private String city;
    private String image;
}

