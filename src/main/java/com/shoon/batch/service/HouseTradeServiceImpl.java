package com.shoon.batch.service;

import com.shoon.batch.repository.entity.PropertyPrice;
import com.shoon.batch.repository.jpa.PropertyPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseTradeServiceImpl implements HouseTradeService {
    private final PropertyPriceRepository propertyPriceRepository;

    @Override
    public void saveHouseTradeAll(List<PropertyPrice> list) {
        propertyPriceRepository.saveAll(list);
    }

    @Override
    public void saveHouseTrade(PropertyPrice propertyPrice) {
        propertyPriceRepository.save(propertyPrice);
    }
}