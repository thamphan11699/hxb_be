package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {}
