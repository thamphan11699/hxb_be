package com.anhtq.app.admin.controller.usercontroller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EditUserPostServiceRequest {

  @NotNull @Email private String email;

  @NotNull private String firstName;

  @NotNull private String lastName;

  @NotNull private String phoneNumber;

  private String avatar;

  @NotNull private Long roleId;
}
