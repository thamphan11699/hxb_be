package com.anhtq.app.admin.controller.booking.serviceresponse;

import lombok.*;

@Data
@Builder
public class CreateBookingGetServiceResponse {

  private Long value;

  private String label;

  private Long price;
}
