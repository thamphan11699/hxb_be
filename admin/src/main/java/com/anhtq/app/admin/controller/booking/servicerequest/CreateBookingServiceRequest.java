package com.anhtq.app.admin.controller.booking.servicerequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingServiceRequest {

  @NotNull
  private String userType;

  private Long userId;

  private Long discountId;

  @NotNull
  private Long serviceId;

  @NotNull
  private Long bookingDate;

  private String email;

  private String firstName;

  private String lastName;

  private String phoneNumber;
}
