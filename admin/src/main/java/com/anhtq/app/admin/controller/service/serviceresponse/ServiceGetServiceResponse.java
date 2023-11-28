package com.anhtq.app.admin.controller.service.serviceresponse;

import java.util.List;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceGetServiceResponse {

  private Long totalElements;

  private Integer pageIndex;

  private List<ServiceItemGetServiceResponse> items;
}
