package com.sensibull.assignment.repository;

import com.sensibull.assignment.entity.UnderlyingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnderlyingPriceRepository extends JpaRepository<UnderlyingEntity,Long> {

    @Modifying
    @Transactional
    @Query(value = "update underlying_stock_price set price =:price where token=:token", nativeQuery = true)
    void updatePriceForToken(Integer token, Double price);

}
