package com.anhtq.app.admin.service.imageservice;

import com.anhtq.app.admin.controller.image.servicerequest.CreateFileServiceRequest;
import com.anhtq.app.admin.controller.image.serviceresponse.CreateFileServiceResponse;
import com.anhtq.app.admin.controller.image.serviceresponse.ImageServiceResponse;
import com.anhtq.app.admin.excetion.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

  private static final List<String> IMAGE_EXT = List.of("png", "jpeg", "jpg", "avif");

  private static final String HTTP_LOCALHOST_9999_API_V_1_IMAGE =
      "http://localhost:9999/api/v1/image/%s";

  @Override
  public ImageServiceResponse uploadImage(MultipartFile multipartFile) {
    try {
      String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
      if (!IMAGE_EXT.contains(ext)) {
        throw new ApiException("File not valid", HttpStatus.BAD_REQUEST);
      }
      Path path = Paths.get("upload-image");
      if (!Files.exists(path)) {
        Files.createDirectories(path);
      }

      String fileNameNew = UUID.randomUUID().toString() + "." + ext;
      try (InputStream inputStream = multipartFile.getInputStream()) {
        Path filePath = path.resolve(fileNameNew);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return ImageServiceResponse.builder()
            .uri(HTTP_LOCALHOST_9999_API_V_1_IMAGE.formatted(fileNameNew))
            .fileName(fileNameNew)
            .fileSize(multipartFile.getSize())
            .build();
      }
    } catch (Exception e) {
      log.error(e.toString());
      throw new ApiException("Upload fail", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void downloadImage(HttpServletResponse response, String filename) throws IOException {
    File file = new File("upload-image/" + filename);
    if (file.exists()) {
      String contentType = "application/octet-stream";
      response.setContentType(contentType);
      OutputStream out = response.getOutputStream();
      FileInputStream in = new FileInputStream(file);
      // copy from in to out
      IOUtils.copy(in, out);
      out.close();
      in.close();
    } else {
      throw new ApiException("Cant not get image", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public CreateFileServiceResponse uploadFile(CreateFileServiceRequest request) {
    try {
      String ext = StringUtils.getFilenameExtension(request.getUpload().getOriginalFilename());
      if (!IMAGE_EXT.contains(ext)) {
        throw new ApiException("File not valid", HttpStatus.BAD_REQUEST);
      }
      Path path = Paths.get("upload-image");
      if (!Files.exists(path)) {
        Files.createDirectories(path);
      }

      String fileNameNew = UUID.randomUUID().toString() + "." + ext;
      try (InputStream inputStream = request.getUpload().getInputStream()) {
        Path filePath = path.resolve(fileNameNew);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return CreateFileServiceResponse.builder()
            .uploaded(true)
            .url(HTTP_LOCALHOST_9999_API_V_1_IMAGE.formatted(fileNameNew))
            .build();
      }
    } catch (Exception e) {
      log.error(e.toString());
      throw new ApiException("Upload fail", HttpStatus.BAD_REQUEST);
    }
  }
}
