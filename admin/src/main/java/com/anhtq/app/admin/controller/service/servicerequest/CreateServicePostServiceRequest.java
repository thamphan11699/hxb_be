package com.anhtq.app.admin.controller.service.servicerequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateServicePostServiceRequest {

  @NotNull private String name;

  private String sortDescription;

  private String description;

  private String image;

  private Long categoryId;

  private Long price;

  private String introduction;
}
