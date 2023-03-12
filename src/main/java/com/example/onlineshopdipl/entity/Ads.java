package com.example.onlineshopdipl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "Ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private Integer price;
    private String image;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ads")
    private List<Image> images;

    @OneToMany(mappedBy = "ads")
    private Set<Comment> comments;

}
