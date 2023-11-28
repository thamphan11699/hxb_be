package com.anhtq.app.admin.controller.discount;

import com.anhtq.app.admin.controller.discount.request.GetDiscountServiceRequest;
import com.anhtq.app.admin.controller.discount.request.InsertOrDiscountServiceRequest;
import com.anhtq.app.admin.controller.discount.response.GetDiscountDetailServiceResponse;
import com.anhtq.app.admin.controller.discount.response.GetDiscountsServiceResponse;
import com.anhtq.app.admin.service.discountservice.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount")
public class DiscountController {

  private final DiscountService discountService;

  @PostMapping("/create")
  public void insert(@RequestBody InsertOrDiscountServiceRequest request) {
    discountService.insertDiscount(request);
  }

  @PostMapping("/update/{id}")
  public void update(
      @RequestBody InsertOrDiscountServiceRequest request, @PathVariable("id") Long id) {
    discountService.updateDiscount(request, id);
  }

  @GetMapping("/{id}")
  public GetDiscountDetailServiceResponse getDiscount(@PathVariable("id") Long id) {
    return discountService.findDiscountById(id);
  }

  @GetMapping("")
  public GetDiscountsServiceResponse getDiscount(
      @ModelAttribute GetDiscountServiceRequest request) {
    return discountService.getDiscounts(request);
  }

  @PostMapping("/delete/{id}")
  public void deleteDiscount(@PathVariable("id") Long id) {
    discountService.deleteDiscount(id);
  }
}
