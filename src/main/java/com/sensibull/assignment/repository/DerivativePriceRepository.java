package com.sensibull.assignment.repository;

import com.sensibull.assignment.entity.DerivativeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DerivativePriceRepository extends JpaRepository<DerivativeEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "update derivative_stock_price set price =:price where derivative_token=:token", nativeQuery = true)
    void updatePriceForToken(Integer token, Double price);

    @Query(value = "select * from derivative_stock_price where underlying=:underlying", nativeQuery = true)
    List<DerivativeEntity> fetchAllDerivativesByUnderLying(String underlying);
}
