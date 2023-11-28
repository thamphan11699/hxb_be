package com.anhtq.app.admin.controller.authcontroller.servicerequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogInServiceRequest {

  @NotNull @Email private String email;

  @NotNull private String password;
}
