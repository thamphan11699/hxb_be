package com.anhtq.app.admin.controller.customer.response;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetHomePageServiceServiceResponse {

  private Long id;

  private String name;

  private String image;

  private Long price;
}
