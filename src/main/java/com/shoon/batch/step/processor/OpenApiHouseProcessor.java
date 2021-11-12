package com.shoon.batch.step.processor;

import com.shoon.batch.dto.HouseTradeDTO;
import com.shoon.batch.repository.entity.PropertyPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@StepScope
public class OpenApiHouseProcessor implements ItemProcessor<List<HouseTradeDTO>, List<PropertyPrice>> {
    @Override
    public List<PropertyPrice> process(List<HouseTradeDTO> list) throws Exception {
        if (list == null) return null;

        List<PropertyPrice> ret = new ArrayList<>();

        for (HouseTradeDTO dto : list) {
            PropertyPrice propertyPrice = PropertyPrice.builder()
                    .amount(dto.get거래금액().trim())
                    .area(Double.parseDouble(dto.get전용면적().trim()))
                    .constructionYear(Integer.parseInt(dto.get건축년도().trim()))
                    .date(dto.get년().trim() + "-" + dto.get월().trim() + "-" + dto.get일().trim())
                    .dong(dto.get법정동().trim())
                    .floor(Integer.parseInt(dto.get층().trim()))
                    .landNum(dto.get대지권면적().trim())
                    .locCode(dto.get지번().trim())
                    .name(dto.get연립다세대().trim())
                    .build();
            ret.add(propertyPrice);
        }
        return ret;
    }
}