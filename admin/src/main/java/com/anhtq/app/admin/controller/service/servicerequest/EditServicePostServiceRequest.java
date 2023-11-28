package com.anhtq.app.admin.controller.service.servicerequest;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditServicePostServiceRequest {

  @NotNull private String name;

  private String sortDescription;

  private String description;

  private String image;

  private Long categoryId;

  private Long price;

  private String introduction;
}
