package com.anhtq.app.admin.controller.usercontroller.response;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRoleGetServiceResponse {

  private Long roleId;

  private String roleName;
}
