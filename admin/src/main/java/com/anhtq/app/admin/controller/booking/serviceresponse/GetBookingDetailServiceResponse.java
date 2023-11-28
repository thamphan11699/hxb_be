package com.anhtq.app.admin.controller.booking.serviceresponse;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookingDetailServiceResponse {

  private Long id;

  private String userType;

  private String customerLastName;

  private String userLastName;

  private String serviceName;

  private String status;

  private LocalDateTime bookingDate;

  private String bookingDateString;

  private String discountName;

  private Long cost;

  private Long promotionalPrice;

  private String customerFirstName;

  private String userFirstName;

  private String customerPhoneNumber;

  private String userPhoneNumber;

  public GetBookingDetailServiceResponse(
      Long id,
      String userType,
      String customerName,
      String userName,
      String serviceName,
      String status,
      LocalDateTime bookingDate,
      String discountName,
      Long cost,
      Long promotionalPrice,
      String customerFirstName,
      String userFirstName,
      String customerPhoneNumber,
      String userPhoneNumber) {
    this.id = id;
    this.userType = userType;
    this.customerLastName = customerName;
    this.userLastName = userName;
    this.serviceName = serviceName;
    this.status = status;
    this.bookingDate = bookingDate;
    this.discountName = discountName;
    this.cost = cost;
    this.promotionalPrice = promotionalPrice;
    this.customerFirstName = customerFirstName;
    this.userFirstName = userFirstName;
    this.customerPhoneNumber = customerPhoneNumber;
    this.userPhoneNumber = userPhoneNumber;
  }
}
