package com.anhtq.app.admin.service.service;

import com.anhtq.app.admin.controller.service.servicerequest.CreateServicePostServiceRequest;
import com.anhtq.app.admin.controller.service.servicerequest.EditServicePostServiceRequest;
import com.anhtq.app.admin.controller.service.servicerequest.ServiceGetServiceRequest;
import com.anhtq.app.admin.controller.service.serviceresponse.CreateServiceGetServiceResponse;
import com.anhtq.app.admin.controller.service.serviceresponse.EditServiceGetServiceResponse;
import com.anhtq.app.admin.controller.service.serviceresponse.ServiceGetServiceResponse;

public interface ServiceService {

    CreateServiceGetServiceResponse createServiceGet();

    void createServicePost(CreateServicePostServiceRequest request);

    ServiceGetServiceResponse getServices(ServiceGetServiceRequest request);

    void deleteService(Long id);

    EditServiceGetServiceResponse getServiceById(Long id);

    void editServiceById(Long id, EditServicePostServiceRequest request);
}
