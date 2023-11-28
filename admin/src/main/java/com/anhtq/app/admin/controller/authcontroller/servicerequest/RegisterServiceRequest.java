package com.anhtq.app.admin.controller.authcontroller.servicerequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterServiceRequest {

  @Email @NotNull private String email;

  @NotNull private String password;

  @NotNull private String passwordConfirm;

  @NotNull private String firstName;

  @NotNull private String lastName;

  @NotNull private String phoneNumber;

  private String image;

  @NotNull private List<Long> roleIds;
}
