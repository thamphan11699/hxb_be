package com.anhtq.app.admin.controller.authcontroller.serviceresponse;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationCategoryResponse {

  private Long value;

  private String label;
}
