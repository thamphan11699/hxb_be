package com.anhtq.app.admin.controller.category.serviceresponse;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesGetServiceResponse {

    private Long totalElements;

    private Integer pageIndex;

    private List<CategoryItemGetServiceResponse> items;
}
