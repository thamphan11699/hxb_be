package com.anhtq.app.admin.controller.service.servicerequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceGetServiceRequest {

    private Integer pageSize;

    private Integer pageIndex;

    private String searchWord;

    private String orderBy;

    private String orderType;
}
