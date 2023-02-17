package com.example.onlineshopdipl.entity;

import com.example.onlineshopdipl.dto.Role;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "Users")
public class User {
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    private String phone;
    @Column(name = "reg_date")
    private LocalDateTime regDate;
    private String city;
    private String image;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String username; //поменяла login на username
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user")
    private Set<Ads> ads;

    @OneToMany(mappedBy = "user")
    private Set<Image> images;

}
