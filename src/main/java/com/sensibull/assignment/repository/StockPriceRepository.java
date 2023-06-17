package com.sensibull.assignment.repository;

import com.sensibull.assignment.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRepository extends JpaRepository<StockEntity,Long> {
}
