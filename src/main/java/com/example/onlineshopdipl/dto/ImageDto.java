package com.example.onlineshopdipl.dto;

import com.example.onlineshopdipl.entity.Ads;
import lombok.Data;

@Data
public class ImageDto {

    private Integer id;
    private byte[] image;
    private Ads ads;
}
