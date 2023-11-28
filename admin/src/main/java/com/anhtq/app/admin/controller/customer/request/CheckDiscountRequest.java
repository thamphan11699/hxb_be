package com.anhtq.app.admin.controller.customer.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDiscountRequest {

    @NonNull
    private String code;

    private Long totalPrice;
}
