package com.example.onlineshopdipl.dto;

import lombok.Data;


@Data

//@RequiredTypes({"description", "price", "title"})

public class CreateAds {
    private String description;
    private Integer price;
    private String title;
}
