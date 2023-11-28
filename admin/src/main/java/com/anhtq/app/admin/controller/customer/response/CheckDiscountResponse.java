package com.anhtq.app.admin.controller.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDiscountResponse {

  private Long id;

  private Long price;
}
