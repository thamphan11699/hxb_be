package com.anhtq.app.admin.excetion;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

  private final String message;
}
