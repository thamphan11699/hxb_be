package com.anhtq.app.admin.controller.discount.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDiscountServiceRequest {

  private Integer pageSize;

  private Integer pageIndex;

  private String searchWord;

  private String orderBy;

  private String orderType;
}
