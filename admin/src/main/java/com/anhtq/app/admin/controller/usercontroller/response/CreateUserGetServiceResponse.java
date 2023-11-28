package com.anhtq.app.admin.controller.usercontroller.response;

import java.util.List;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserGetServiceResponse {

  private List<CreateUserRoleGetServiceResponse> roles;
}
