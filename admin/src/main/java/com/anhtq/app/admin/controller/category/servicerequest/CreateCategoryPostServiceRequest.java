package com.anhtq.app.admin.controller.category.servicerequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryPostServiceRequest {

  @NotNull private String name;

  @NotNull private Boolean isHome;
}
