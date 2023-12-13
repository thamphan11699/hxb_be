package com.anhtq.app.admin.controller.dashborad;

import com.anhtq.app.admin.controller.dashborad.serviceresponse.DashBoardGetServiceResponse;
import com.anhtq.app.admin.service.dashboard.DashBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashBoardController {

  private final DashBoardService dashBoardService;

  @Secured({"ROLE_ADMIN"})
  @GetMapping("")
  public DashBoardGetServiceResponse getCategories() {
    return dashBoardService.getDashBoard();
  }
}
