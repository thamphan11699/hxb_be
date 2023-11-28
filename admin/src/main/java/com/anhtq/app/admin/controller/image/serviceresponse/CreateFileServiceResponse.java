package com.anhtq.app.admin.controller.image.serviceresponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateFileServiceResponse {

  private Boolean uploaded;

  private String url;
}
