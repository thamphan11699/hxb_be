package com.anhtq.app.admin.controller.customer.response;

import java.util.List;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetServicePageServiceResponse {

  private List<GetServicePageItemServiceResponse> services;

  private Long count;
}
