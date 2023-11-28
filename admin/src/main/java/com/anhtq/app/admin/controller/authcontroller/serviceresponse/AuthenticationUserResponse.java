package com.anhtq.app.admin.controller.authcontroller.serviceresponse;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationUserResponse {

  private Long id;

  private String email;

  private String avatar;

  private List<String> roles;
}
