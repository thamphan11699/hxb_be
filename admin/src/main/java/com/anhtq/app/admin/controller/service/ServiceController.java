package com.anhtq.app.admin.controller.service;

import com.anhtq.app.admin.controller.authcontroller.serviceresponse.AuthenticationResponse;
import com.anhtq.app.admin.controller.service.servicerequest.CreateServicePostServiceRequest;
import com.anhtq.app.admin.controller.service.servicerequest.EditServicePostServiceRequest;
import com.anhtq.app.admin.controller.service.servicerequest.ServiceGetServiceRequest;
import com.anhtq.app.admin.controller.service.serviceresponse.CreateServiceGetServiceResponse;
import com.anhtq.app.admin.controller.service.serviceresponse.EditServiceGetServiceResponse;
import com.anhtq.app.admin.controller.service.serviceresponse.ServiceGetServiceResponse;
import com.anhtq.app.admin.controller.usercontroller.response.CreateUserGetServiceResponse;
import com.anhtq.app.admin.service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
public class ServiceController {

  private final ServiceService service;

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/create")
  public CreateServiceGetServiceResponse createUserGet() {
    return service.createServiceGet();
  }
  @Secured({"ROLE_ADMIN"})
  @PostMapping("/create")
  public void createServicePost(@RequestBody CreateServicePostServiceRequest request) {
    service.createServicePost(request);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("")
  public ServiceGetServiceResponse getServices(@ModelAttribute ServiceGetServiceRequest request) {
    return service.getServices(request);
  }

  @Secured({"ROLE_ADMIN"})
  @PostMapping("/delete/{id}")
  public void deleteServicePost(@PathVariable("id") Long id) {
    service.deleteService(id);
  }

  @Secured({"ROLE_ADMIN"})
  @GetMapping("/edit/{id}")
  public EditServiceGetServiceResponse editServiceGet(@PathVariable("id") Long id) {
    return service.getServiceById(id);
  }

  @Secured({"ROLE_ADMIN"})
  @PostMapping("/edit/{id}")
  public void editServicePost(
      @RequestBody EditServicePostServiceRequest request, @PathVariable("id") Long id) {
    service.editServiceById(id, request);
  }
}
