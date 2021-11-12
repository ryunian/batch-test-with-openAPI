package com.shoon.batch.service;


import com.shoon.batch.repository.entity.LawDong;
import com.shoon.batch.repository.jpa.LawDongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
 public class LawDongServiceImpl implements LawDongService {
    private final LawDongRepository addressSiGuJpaRepository;

    @Override
    public List<LawDong> getAllAddressSiGu() {
        return addressSiGuJpaRepository.findAll();
    }
 }