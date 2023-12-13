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
public class GetMyBookingServiceResponse {

  private Long id;

  private String serviceImg;

  private String serviceName;

  private String sortDescription;

  private String status;

  private LocalDateTime bookingDate;

  private String bookingDateString;

  private String discountName;

  private Long cost;

  private Long promotionalPrice;

  public GetMyBookingServiceResponse(
      Long id,
      String serviceImg,
      String serviceName,
      String desc,
      String status,
      LocalDateTime bookingDate,
      String discountName,
      Long cost,
      Long promotionalPrice
      ) {
    this.id = id;
    this.serviceImg = serviceImg;
    this.serviceName = serviceName;
    this.status = status;
    this.bookingDate = bookingDate;
    this.discountName = discountName;
    this.cost = cost;
    this.promotionalPrice = promotionalPrice;
    this.sortDescription = desc;
  }
}
