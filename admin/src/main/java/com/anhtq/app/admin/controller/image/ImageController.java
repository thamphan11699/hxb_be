package com.anhtq.app.admin.controller.image;

import com.anhtq.app.admin.controller.image.servicerequest.CreateFileServiceRequest;
import com.anhtq.app.admin.controller.image.serviceresponse.CreateFileServiceResponse;
import com.anhtq.app.admin.controller.image.serviceresponse.ImageServiceResponse;
import com.anhtq.app.admin.service.imageservice.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

  private final ImageService imageService;

  @PostMapping("/upload")
  public ImageServiceResponse uploadFile(@RequestParam("file") MultipartFile multipartFile) {
    return imageService.uploadImage(multipartFile);
  }

  @PostMapping("/upload-ck")
  public CreateFileServiceResponse uploadFileCk(@ModelAttribute CreateFileServiceRequest request) {
    return imageService.uploadFile(request);
  }

  @GetMapping("/{fileName}")
  public void downloadImage(HttpServletResponse response, @PathVariable("fileName") String fileName)
      throws IOException {
    imageService.downloadImage(response, fileName);
  }
}
