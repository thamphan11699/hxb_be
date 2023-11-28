package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.CategoriesEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Long> {

  Optional<CategoriesEntity> findByName(String name);
}
