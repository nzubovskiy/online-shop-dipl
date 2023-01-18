package com.example.onlineshopdipl.dto;

import lombok.Data;

@Data
public class Ads {
    private Integer author;
    private String[] image;
    private Integer pk;
    private Integer price;
    private String title;
}
