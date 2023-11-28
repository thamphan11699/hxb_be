package com.anhtq.app.admin.controller.usercontroller.response;

import java.util.List;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EditUserGetServiceResponse {

  private Long id;

  private String email;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private String avatar;

  private String password;

  private String passwordConfirm;

  private Long roleId;

  private List<CreateUserRoleGetServiceResponse> roles;
}
