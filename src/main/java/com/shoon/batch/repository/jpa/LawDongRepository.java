package com.shoon.batch.repository.jpa;

import java.util.List;

import com.shoon.batch.repository.entity.LawDong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LawDongRepository extends JpaRepository<LawDong, Long> {
    List<LawDong> findAll();
}