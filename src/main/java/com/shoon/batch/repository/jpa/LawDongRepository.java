package com.shoon.batch.repository.jpa;

import java.util.List;

import com.shoon.batch.repository.entity.LawDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LawDongRepository extends JpaRepository<LawDong, Long> {
    List<LawDong> findAll();

    @Query(value = "select * from law_dong group by floor(ld_code / 100000)", nativeQuery = true)
    List<LawDong> findAllGroupByLawdCode();
}