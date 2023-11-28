package com.anhtq.app.admin.controller.category.serviceresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryItemGetServiceResponse {

  private Long id;

  private String name;

  private Boolean isHome;
}
