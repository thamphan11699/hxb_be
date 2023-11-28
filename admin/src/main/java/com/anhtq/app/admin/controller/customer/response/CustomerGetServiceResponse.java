package com.anhtq.app.admin.controller.customer.response;

import lombok.*;

import java.util.List;

@Data
@Builder
public class CustomerGetServiceResponse {

  private Long totalElements;

  private Integer pageIndex;

  private List<CustomerItemGetServiceResponse> items;
}
