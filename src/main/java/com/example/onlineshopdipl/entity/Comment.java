package com.example.onlineshopdipl.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Comments")
public class Comment {
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ads_pk")
    private Ads ads;
}
