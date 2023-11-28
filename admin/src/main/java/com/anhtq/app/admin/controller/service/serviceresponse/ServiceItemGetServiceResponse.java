package com.anhtq.app.admin.controller.service.serviceresponse;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemGetServiceResponse {
  private Long id;

  private String name;

  private String sortDescription;

  private String description;

  private String image;
}
