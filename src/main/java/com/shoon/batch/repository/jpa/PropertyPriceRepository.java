package com.shoon.batch.repository.jpa;


import com.shoon.batch.repository.entity.PropertyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Repository
public interface PropertyPriceRepository extends JpaRepository<PropertyPrice, Long> {
}