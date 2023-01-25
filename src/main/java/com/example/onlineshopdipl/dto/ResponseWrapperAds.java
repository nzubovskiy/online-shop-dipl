package com.example.onlineshopdipl.dto;

import com.example.onlineshopdipl.entity.Ads;
import lombok.Data;

@Data
public class ResponseWrapperAds {
    private Integer count;
    private Ads results;
}
