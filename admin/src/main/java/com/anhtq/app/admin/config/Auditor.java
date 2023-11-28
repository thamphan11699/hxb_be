package com.anhtq.app.admin.config;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class Auditor implements AuditorAware<String> {

  @Override
  @NotNull
  public Optional<String> getCurrentAuditor() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return !"anonymousUser".equals(username) ? Optional.of(username) : Optional.of("API");
  }
}
