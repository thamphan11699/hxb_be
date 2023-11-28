package com.anhtq.app.admin.service.imageservice;

import com.anhtq.app.admin.controller.image.servicerequest.CreateFileServiceRequest;
import com.anhtq.app.admin.controller.image.serviceresponse.CreateFileServiceResponse;
import com.anhtq.app.admin.controller.image.serviceresponse.ImageServiceResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

  ImageServiceResponse uploadImage(MultipartFile multipartFile);

  void downloadImage(HttpServletResponse response, String filename) throws IOException;

  CreateFileServiceResponse uploadFile(CreateFileServiceRequest request);
}
