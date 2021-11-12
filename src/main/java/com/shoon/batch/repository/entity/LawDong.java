package com.shoon.batch.repository.entity;

import lombok.*;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
public class LawDong implements Serializable {
    @Id
    long ldCode;
    String ldCity;
    String ldLoc;
    String ldDong;

    @Builder
    public LawDong(long ldCode, String ldCity, String ldLoc, String ldDong) {
        this.ldCode = ldCode;
        this.ldCity = ldCity;
        this.ldLoc = ldLoc;
        this.ldDong = ldDong;
    }
}