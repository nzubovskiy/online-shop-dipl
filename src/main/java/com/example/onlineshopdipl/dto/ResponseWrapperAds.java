package com.example.onlineshopdipl.dto;

import com.example.onlineshopdipl.entity.Ads;
import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {
    private Integer count;
    private List<AdsDto> results;

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setResults(List<AdsDto> results) {
        this.results = results;
    }
}
