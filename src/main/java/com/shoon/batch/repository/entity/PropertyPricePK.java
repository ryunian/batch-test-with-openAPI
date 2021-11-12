package com.shoon.batch.repository.entity;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PropertyPricePK implements Serializable {
    private String date; // 계약월
    private String name; // 건물명
}
