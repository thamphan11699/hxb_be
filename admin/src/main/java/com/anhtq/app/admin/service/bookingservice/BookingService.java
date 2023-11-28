package com.anhtq.app.admin.service.bookingservice;

import com.anhtq.app.admin.controller.booking.servicerequest.CreateBookingServiceRequest;
import com.anhtq.app.admin.controller.booking.servicerequest.GetBookingServiceRequest;
import com.anhtq.app.admin.controller.booking.servicerequest.UpdateStatusRequest;
import com.anhtq.app.admin.controller.booking.serviceresponse.CreateBookingGetServiceResponse;
import com.anhtq.app.admin.controller.booking.serviceresponse.GetBookingDetailServiceResponse;
import com.anhtq.app.admin.controller.booking.serviceresponse.GetBookingServiceResponse;

import java.util.List;

public interface BookingService {

  void booking(CreateBookingServiceRequest request);

  List<CreateBookingGetServiceResponse> getBooking();

  GetBookingServiceResponse getBookings(GetBookingServiceRequest request);

  GetBookingDetailServiceResponse getBookingById(Long id);

  void updateStatus(UpdateStatusRequest request, Long id);

  List<GetBookingDetailServiceResponse> getBookingByUserId(Long userId);
}
