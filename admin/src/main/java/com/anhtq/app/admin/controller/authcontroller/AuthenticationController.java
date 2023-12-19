package com.anhtq.app.admin.controller.authcontroller;

import com.anhtq.app.admin.controller.authcontroller.servicerequest.LogInServiceRequest;
import com.anhtq.app.admin.controller.authcontroller.servicerequest.RegisterServiceRequest;
import com.anhtq.app.admin.controller.authcontroller.serviceresponse.AuthenticationResponse;
import com.anhtq.app.admin.service.authservice.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/authentication")
  public AuthenticationResponse authentication(@RequestBody LogInServiceRequest request) {
    log.info(request.toString());
    return authenticationService.authenticate(request);
  }

  @PostMapping("/authentication-customer")
  public AuthenticationResponse authenticationCustomer(@RequestBody LogInServiceRequest request) {
    log.info(request.toString());
    return authenticationService.authenticateCustomer(request);
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthenticationResponse register(@RequestBody RegisterServiceRequest request) {
    log.info(request.toString());
    return authenticationService.register(request);
  }
}
