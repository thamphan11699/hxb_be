package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.CustomerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

  Optional<CustomerEntity> findByEmail(String email);
}
