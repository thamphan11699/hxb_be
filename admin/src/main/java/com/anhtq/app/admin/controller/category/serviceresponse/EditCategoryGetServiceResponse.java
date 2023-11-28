package com.anhtq.app.admin.controller.category.serviceresponse;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EditCategoryGetServiceResponse {

  private Long id;

  private String name;

  private Boolean isHome;
}
