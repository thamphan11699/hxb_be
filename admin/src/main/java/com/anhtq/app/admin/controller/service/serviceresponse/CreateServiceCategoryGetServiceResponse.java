package com.anhtq.app.admin.controller.service.serviceresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateServiceCategoryGetServiceResponse {

  private String label;

  private Long value;
}
