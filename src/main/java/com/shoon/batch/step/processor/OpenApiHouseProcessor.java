package com.shoon.batch.step.processor;

import com.shoon.batch.dto.HouseTradeDTO;
import com.shoon.batch.repository.entity.PropertyPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@StepScope
public class OpenApiHouseProcessor implements ItemProcessor<List<HouseTradeDTO>, List<PropertyPrice>> {
    @Override
    public List<PropertyPrice> process(List<HouseTradeDTO> list) throws Exception {
//        if (list == null) return null;

        return list.stream().map(
                item -> PropertyPrice.builder()
                        .amount(item.get거래금액().trim())
                        .area(Double.parseDouble(item.get전용면적().trim()))
                        .constructionYear(Integer.parseInt(item.get건축년도().trim()))
                        .date(item.get년().trim() + "-" + item.get월().trim() + "-" + item.get일().trim())
                        .dong(item.get법정동().trim())
                        .floor(Integer.parseInt(item.get층().trim()))
                        .landNum(item.get대지권면적().trim())
                        .locCode(item.get지번().trim())
                        .name(item.get연립다세대().trim())
                        .build())
                .collect(Collectors.toList());
    }
}