package com.shoon.batch.dto;

import com.shoon.batch.repository.entity.PropertyPrice;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class HouseTradeDTO implements Serializable {
    private String 거래금액;
    private String 거래유형;
    private String 건축년도;
    private String 년;
    private String 대지권면적;
    private String 법정동;
    private String 연립다세대;
    private String 월;
    private String 일;
    private String 전용면적;
    private String 중개사소재지;
    private String 지번;
    private String 지역코드;
    private String 층;
    private String 해제사유발생일;
    private String 해제여부;
}