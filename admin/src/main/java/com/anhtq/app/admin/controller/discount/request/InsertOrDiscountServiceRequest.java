package com.anhtq.app.admin.controller.discount.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertOrDiscountServiceRequest {

  @NonNull private Integer percent;

  @NonNull private Long expiredAt;

  private Long minimumOrder;

  private Long maximumMoney;
}
