package com.anhtq.app.admin.controller.discount.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDiscountsServiceResponse {

  private Long totalElements;

  private Integer pageIndex;

  private List<GetDiscountDetailServiceResponse> items;
}
