package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.ServiceEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

  Optional<ServiceEntity> findByName(String name);
}
