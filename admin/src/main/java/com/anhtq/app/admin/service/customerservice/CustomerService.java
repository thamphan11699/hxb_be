package com.anhtq.app.admin.service.customerservice;

import com.anhtq.app.admin.controller.customer.request.CustomerGetServiceRequest;
import com.anhtq.app.admin.controller.customer.response.CustomerGetServiceResponse;
import com.anhtq.app.admin.controller.customer.response.GetHomePageServiceResponse;
import com.anhtq.app.admin.controller.customer.response.GetServiceDetailPageServiceResponse;
import com.anhtq.app.admin.controller.customer.response.GetServicePageServiceResponse;

public interface CustomerService {

  GetHomePageServiceResponse getHomePage();

  GetServicePageServiceResponse getServicePage(Integer start);

  GetServiceDetailPageServiceResponse getServiceDetailPage(Long id);

  CustomerGetServiceResponse getCustomers(CustomerGetServiceRequest request);
}
