package com.anhtq.app.admin.controller.customer.response;

import java.util.List;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetHomePageServiceResponse {

  private List<String> slideUrls;

  private List<GetHomePageServiceServiceResponse> services;
}
