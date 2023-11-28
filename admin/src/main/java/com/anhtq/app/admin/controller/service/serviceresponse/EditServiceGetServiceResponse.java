package com.anhtq.app.admin.controller.service.serviceresponse;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditServiceGetServiceResponse {

    private Long id;

    private String name;

    private String sortDescription;

    private String description;

    private String image;

    private Long categoryId;

    private Long price;

    private String introduction;

    private List<CreateServiceCategoryGetServiceResponse> categories;
}
