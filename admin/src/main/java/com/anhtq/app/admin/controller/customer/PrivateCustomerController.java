package com.anhtq.app.admin.controller.customer;

import com.anhtq.app.admin.controller.customer.request.CustomerGetServiceRequest;
import com.anhtq.app.admin.controller.customer.response.CustomerGetServiceResponse;
import com.anhtq.app.admin.service.customerservice.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_ADMIN"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/private-customer")
public class PrivateCustomerController {

  private final CustomerService customerService;

  @GetMapping("")
  public CustomerGetServiceResponse getCustomers(
      @ModelAttribute CustomerGetServiceRequest request) {
    return customerService.getCustomers(request);
  }
}
