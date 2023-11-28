package com.anhtq.app.admin.controller.authcontroller.serviceresponse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthenticationResponse {

  private String token;

  private AuthenticationUserResponse user;
}
