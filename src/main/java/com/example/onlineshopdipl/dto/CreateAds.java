package com.example.onlineshopdipl.dto;

import lombok.Data;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.stereotype.Component;

@Data
@RequiredTypes({"description", "price", "title"})
public class CreateAds {
    private String description;
    private Integer price;
    private String title;
}
