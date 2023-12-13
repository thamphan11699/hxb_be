package com.anhtq.app.admin.repository;

import com.anhtq.app.admin.doamin.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity, Long> {

  @Query(
      value = "SELECT SUM(COALESCE(tb.promotional_price, 0)) FROM public.tbl_booking_detail AS tb",
      nativeQuery = true)
  Long countIncome();
}
