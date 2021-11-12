package com.shoon.batch.service;

import com.shoon.batch.repository.entity.PropertyPrice;

import java.util.List;


public interface HouseTradeService {
    void saveHouseTradeAll(List<PropertyPrice> list);
    void saveHouseTrade(PropertyPrice propertyPrice);
 }