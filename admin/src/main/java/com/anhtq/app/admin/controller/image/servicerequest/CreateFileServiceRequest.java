package com.anhtq.app.admin.controller.image.servicerequest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateFileServiceRequest {

  private MultipartFile upload;

  private String ckCsrfToken;
}
