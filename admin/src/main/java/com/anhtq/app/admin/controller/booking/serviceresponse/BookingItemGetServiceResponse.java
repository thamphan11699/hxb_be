package com.anhtq.app.admin.controller.booking.serviceresponse;

import java.time.LocalDateTime;
import lombok.*;

@Data
public class BookingItemGetServiceResponse {

  private Long id;

  private String customerName;

  private String userName;

  private String serviceName;

  private String status;

  private String bookingDate;

  private LocalDateTime bookingDateTime;

  private Long cost;

  private Long promotionalPrice;

  public BookingItemGetServiceResponse(
      Long id,
      String customerName,
      String userName,
      String serviceName,
      String status,
      LocalDateTime bookingDateTime,
      Long cost,
      Long promotionalPrice) {
    this.id = id;
    this.customerName = customerName;
    this.userName = userName;
    this.serviceName = serviceName;
    this.status = status;
    this.bookingDateTime = bookingDateTime;
    this.cost = cost;
    this.promotionalPrice = promotionalPrice;
  }
}
