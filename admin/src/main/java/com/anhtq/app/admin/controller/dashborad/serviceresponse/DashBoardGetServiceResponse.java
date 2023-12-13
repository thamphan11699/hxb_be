package com.anhtq.app.admin.controller.dashborad.serviceresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashBoardGetServiceResponse {

  private final Long customer;

  private final Long user;

  private final Long income;

  private final Long booking;
}
