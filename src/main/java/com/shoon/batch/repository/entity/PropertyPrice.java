package com.shoon.batch.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@NoArgsConstructor
@IdClass(PropertyPricePK.class)
public class PropertyPrice implements Serializable {
    @Id
    private String date;                   // 계약월
    @Id
    private String name;                   // 건물명
    private String amount;                 // 거래금액
    private int constructionYear;          // 건축년도
    private double area;                   // 면적
    private String dong;                   // 법정동
    private String landNum;                // 대지면적
    private String locCode;                // 지역코드
    private int floor;                     // 층수

    @Builder
    public PropertyPrice(String date, String name, String amount, int constructionYear, double area, String dong, String landNum, String locCode, int floor) {
        this.date = date;
        this.name = name;
        this.amount = amount;
        this.constructionYear = constructionYear;
        this.area = area;
        this.dong = dong;
        this.landNum = landNum;
        this.locCode = locCode;
        this.floor = floor;
    }
}