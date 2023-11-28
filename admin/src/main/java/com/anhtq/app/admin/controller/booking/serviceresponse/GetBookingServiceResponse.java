package com.anhtq.app.admin.controller.booking.serviceresponse;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookingServiceResponse {

  private Long totalElements;

  private Integer pageIndex;

  private List<GetBookingDetailServiceResponse> items;
}
