package com.anhtq.app.admin.service.authservice;

import com.anhtq.app.admin.controller.authcontroller.servicerequest.LogInServiceRequest;
import com.anhtq.app.admin.controller.authcontroller.servicerequest.RegisterServiceRequest;
import com.anhtq.app.admin.controller.authcontroller.serviceresponse.AuthenticationResponse;

public interface AuthenticationService {

  AuthenticationResponse authenticate(LogInServiceRequest request);

  AuthenticationResponse register(RegisterServiceRequest request);

  AuthenticationResponse getCurrentUser();
}
