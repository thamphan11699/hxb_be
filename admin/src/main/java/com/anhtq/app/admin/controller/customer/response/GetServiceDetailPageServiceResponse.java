package com.anhtq.app.admin.controller.customer.response;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceDetailPageServiceResponse {

  private String name;

  private Long price;

  private String description;

  private String introduction;
}
