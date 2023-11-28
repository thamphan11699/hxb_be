package com.anhtq.app.admin.controller.customer.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGetServiceRequest {

  private Integer pageSize;

  private Integer pageIndex;

  private String searchWord;

  private String orderBy;

  private String orderType;
}
