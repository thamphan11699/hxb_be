package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {

    Optional<DiscountEntity> findByCode(String code);
}
