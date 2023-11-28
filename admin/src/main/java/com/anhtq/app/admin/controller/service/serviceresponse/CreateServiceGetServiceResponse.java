package com.anhtq.app.admin.controller.service.serviceresponse;

import java.util.List;
import lombok.*;

@Getter
@Builder
public class CreateServiceGetServiceResponse {

  private List<CreateServiceCategoryGetServiceResponse> categories;
}

