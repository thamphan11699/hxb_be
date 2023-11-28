package com.anhtq.app.admin.controller.booking;

import com.anhtq.app.admin.controller.booking.servicerequest.GetBookingServiceRequest;
import com.anhtq.app.admin.controller.booking.servicerequest.UpdateStatusRequest;
import com.anhtq.app.admin.controller.booking.serviceresponse.GetBookingDetailServiceResponse;
import com.anhtq.app.admin.controller.booking.serviceresponse.GetBookingServiceResponse;
import com.anhtq.app.admin.service.bookingservice.BookingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/private/booking")
public class PrivateBookingController {

  private final BookingService bookingService;

  @GetMapping()
  public GetBookingServiceResponse booking(@ModelAttribute GetBookingServiceRequest request) {
    return bookingService.getBookings(request);
  }

  @GetMapping("/{id}")
  public GetBookingDetailServiceResponse booking(@PathVariable("id") Long id) {
    return bookingService.getBookingById(id);
  }

  @PostMapping("/update-status/{id}")
  public void updateStatus(@PathVariable("id") Long id, @RequestBody UpdateStatusRequest request) {
    bookingService.updateStatus(request, id);
  }

  @GetMapping("/user/{id}")
  public List<GetBookingDetailServiceResponse> getBookingByUserId(@PathVariable("id") Long id) {
    return bookingService.getBookingByUserId(id);
  }
}
