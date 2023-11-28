package com.anhtq.app.admin.controller.usercontroller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserItemGetServiceResponse {

  private Long id;

  private String email;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private String status;

  private String avatar;

  private Long roleId;
}
