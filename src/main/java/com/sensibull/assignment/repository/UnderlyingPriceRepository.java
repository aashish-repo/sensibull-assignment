package com.sensibull.assignment.repository;

import com.sensibull.assignment.entity.UnderlyingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnderlyingPriceRepository extends JpaRepository<UnderlyingEntity,Long> {

}
