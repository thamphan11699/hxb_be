package com.anhtq.app.admin.service.discountservice;

import com.anhtq.app.admin.controller.customer.request.CheckDiscountRequest;
import com.anhtq.app.admin.controller.customer.response.CheckDiscountResponse;
import com.anhtq.app.admin.controller.discount.request.GetDiscountServiceRequest;
import com.anhtq.app.admin.controller.discount.request.InsertOrDiscountServiceRequest;
import com.anhtq.app.admin.controller.discount.response.GetDiscountDetailServiceResponse;
import com.anhtq.app.admin.controller.discount.response.GetDiscountsServiceResponse;

public interface DiscountService {

  void insertDiscount(InsertOrDiscountServiceRequest request);

  CheckDiscountResponse checkDiscountCode(CheckDiscountRequest code);

  void updateDiscount(InsertOrDiscountServiceRequest request, Long id);

  GetDiscountDetailServiceResponse findDiscountById (Long id);

  GetDiscountsServiceResponse getDiscounts(GetDiscountServiceRequest request);

  void deleteDiscount(Long id);
}
