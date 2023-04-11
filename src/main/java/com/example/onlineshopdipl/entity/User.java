package com.example.onlineshopdipl.entity;

import com.example.onlineshopdipl.dto.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
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
    private String username; 
    private String password;
    private Boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Ads> ads;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Image> images;

}
