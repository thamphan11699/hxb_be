package com.anhtq.app.admin.service.common;

import com.anhtq.app.admin.excetion.ApiException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

  private final Validator validator;

  @Override
  public void validate(Object request) {
    Map<String, List<String>> result =
        validator.validate(request).stream()
            .collect(
                Collectors.groupingBy(
                    e -> e.getPropertyPath().toString(),
                    Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())));

    if (!CollectionUtils.isEmpty(result)) {
      throw new ApiException("Validate error", HttpStatus.BAD_REQUEST);
    }
  }
}
