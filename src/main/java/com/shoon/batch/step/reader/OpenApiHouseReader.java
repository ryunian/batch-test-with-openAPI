package com.shoon.batch.step.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoon.batch.dto.HouseTradeDTO;
import com.shoon.batch.repository.entity.LawDong;
import com.shoon.batch.repository.jpa.LawDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@StepScope
@RequiredArgsConstructor
public class OpenApiHouseReader implements ItemReader<List<HouseTradeDTO>> {
    private final RestTemplate restTemplate;
    private final LawDongRepository lawDongRepository;

    @Value("${open.api.public.data.servicekey}")
    private String serviceKey;

    @Value("${open.api.public.data.url}")
    private String resturl;

    @Value("#{jobParameters[month]}")
    private String month;

    private int lawDongIndex = 0;
    private List<LawDong> lawDongList;

    @Override
    public List<HouseTradeDTO> read() throws Exception {
        log.debug("Reading the information of the next HouseTrade");
        log.debug("======== nextAddressSiGuIndex : " + lawDongIndex + "=========");

        lawDongInit();

        if (lawDongIndex >= lawDongList.size()) {
            log.debug("nextAddressSiGuIndex is " + lawDongIndex + " and this job has finished.");
            month = null;
            lawDongIndex = 0;
            lawDongList = null;
            return null;
        }

        log.debug("nextAddressSiGuIndex is " + lawDongIndex + " and this job is running.");
        long LawdCode = lawDongList.get(lawDongIndex++).getLdCode() / 100000; // 지역코드만 추출
        return fetchHouseTradeDTOFromAPI(LawdCode, month);
    }

    private void lawDongInit() {
        if (lawDongList == null) {
            log.debug("Address info init..");
            /**
             * TODO : 테이블에 구/군 + 동에 대한 정보가 있어서 구/군에 대한 지역코드가 겹치는 문제
             */
            lawDongList = lawDongRepository.findAll();
        }
    }

    // 연립다세대 OPEN API 호출 , 요쳥변수 : LAWD_CD(지역코드) , DEAL_YMD(계약월)
    private List<HouseTradeDTO> fetchHouseTradeDTOFromAPI(long LawdCode, String month){
        log.debug("Fetching houseTrade data from an external API by using the url: {}", resturl);

        try {
            URI uri = new URI(resturl + "?serviceKey=" + serviceKey + "&LAWD_CD=" + LawdCode + "&DEAL_YMD=" + month);
            log.info("===================================================");
            log.info("Open API URL :" + uri.toString());
            log.info("===================================================");

            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode item = root.path("response").path("body").path("items").get("item");

            return new ObjectMapper().readerFor(new TypeReference<List<HouseTradeDTO>>() {}).readValue(item);

        } catch (Exception e) {
            log.error("[" + LawdCode + "] 단독/다가구 거래 데이터 없음");
        }
        return new ArrayList<>();
    }
}