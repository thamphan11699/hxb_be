package com.anhtq.app.admin.controller.image.serviceresponse;

import lombok.*;

@Getter
@Builder
public class ImageServiceResponse {

  private final String fileName;

  private final Long fileSize;

  private final String uri;
}
