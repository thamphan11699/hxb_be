package com.anhtq.app.admin.controller.usercontroller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserGetServiceRequest {

    private Integer pageSize;

    private Integer pageIndex;

    private String searchWord;

    private String orderBy;

    private String orderType;
}
