package com.anhtq.app.admin.controller.customer;

import com.anhtq.app.admin.controller.customer.request.CheckDiscountRequest;
import com.anhtq.app.admin.controller.customer.response.CheckDiscountResponse;
import com.anhtq.app.admin.controller.customer.response.GetHomePageServiceResponse;
import com.anhtq.app.admin.controller.customer.response.GetServiceDetailPageServiceResponse;
import com.anhtq.app.admin.controller.customer.response.GetServicePageServiceResponse;
import com.anhtq.app.admin.service.customerservice.CustomerService;
import com.anhtq.app.admin.service.discountservice.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

  private final CustomerService customerService;

  private final DiscountService discountService;

  @GetMapping("/home")
  public GetHomePageServiceResponse index() {
    return customerService.getHomePage();
  }

  @GetMapping("/service")
  public GetServicePageServiceResponse getService(@Param("start") Integer start) {
    return customerService.getServicePage(start);
  }

  @GetMapping("/service/{id}")
  public GetServiceDetailPageServiceResponse getServiceDetail(@PathVariable("id") Long id) {
    return customerService.getServiceDetailPage(id);
  }

  @GetMapping("/check-discount")
  public CheckDiscountResponse getServiceDetail(@ModelAttribute CheckDiscountRequest request) {
    return discountService.checkDiscountCode(request);
  }
}
