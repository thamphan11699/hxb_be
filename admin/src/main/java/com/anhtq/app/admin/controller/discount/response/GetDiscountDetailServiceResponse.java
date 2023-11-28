package com.anhtq.app.admin.controller.discount.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDiscountDetailServiceResponse {

  private Long id;

  private String code;

  private Integer percent;

  private LocalDateTime expiredAt;

  private String expiredAtString;

  private Long minimumOrder;

  private Long maximumMoney;

  public GetDiscountDetailServiceResponse(
      Long id,
      String code,
      Integer percent,
      LocalDateTime expiredAt,
      Long minimumOrder,
      Long maximumMoney) {
    this.id = id;
    this.code = code;
    this.percent = percent;
    this.expiredAt = expiredAt;
    this.minimumOrder = minimumOrder;
    this.maximumMoney = maximumMoney;
  }
}
