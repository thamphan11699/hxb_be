package com.anhtq.app.admin.controller.booking;

import com.anhtq.app.admin.controller.booking.servicerequest.CreateBookingServiceRequest;
import com.anhtq.app.admin.controller.booking.serviceresponse.CreateBookingGetServiceResponse;
import com.anhtq.app.admin.service.bookingservice.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingController {

  private final BookingService bookingService;

  @PostMapping("/create-booking")
  public void booking(@RequestBody CreateBookingServiceRequest request) {
    bookingService.booking(request);
  }

  @GetMapping("/create-booking")
  public List<CreateBookingGetServiceResponse> booking() {
    return bookingService.getBooking();
  }
}
