package com.anhtq.app.admin.controller.usercontroller.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetServiceResponse {
    private Long totalElements;

    private Integer pageIndex;

    private List<UserItemGetServiceResponse> items;
}
