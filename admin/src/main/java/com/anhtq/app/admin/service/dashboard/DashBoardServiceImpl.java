package com.anhtq.app.admin.service.dashboard;

import com.anhtq.app.admin.controller.dashborad.serviceresponse.DashBoardGetServiceResponse;
import com.anhtq.app.admin.repository.BookingDetailRepository;
import com.anhtq.app.admin.repository.BookingRepository;
import com.anhtq.app.admin.repository.CustomerRepository;
import com.anhtq.app.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {

  private final UserRepository userRepository;

  private final CustomerRepository customerRepository;

  private final BookingRepository bookingRepository;

  private final BookingDetailRepository bookingDetailRepository;

  @Override
  public DashBoardGetServiceResponse getDashBoard() {

    Long user = userRepository.count();

    Long customer = customerRepository.count();

    Long booking = bookingRepository.count();

    Long income = bookingDetailRepository.countIncome();

    return DashBoardGetServiceResponse.builder()
        .booking(booking)
        .customer(customer)
        .income(income)
        .user(user)
        .build();
  }
}
