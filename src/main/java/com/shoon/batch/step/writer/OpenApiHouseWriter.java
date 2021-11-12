package com.shoon.batch.step.writer;

import com.shoon.batch.repository.entity.PropertyPrice;
import com.shoon.batch.repository.jpa.PropertyPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenApiHouseWriter implements ItemWriter<List<PropertyPrice>> {
    private final PropertyPriceRepository propertyPriceRepository;

    @Override
    public void write(List<? extends List<PropertyPrice>> lists) throws Exception {
        log.info("Received the information of {} array", lists.size());

        for (List<PropertyPrice> list : lists) {
            log.info("연립다세대 실거래가 데이터 수신, ADW 트랜잭션 시작. [거래 건수 : " + list.size() + " 건]");
            propertyPriceRepository.saveAll(list);
            log.info("연립다세대 실거래가 데이터 수신, ADW 트랜잭션 종료. [거래 건수 : " + list.size() + " 건]");
        }
    }
}