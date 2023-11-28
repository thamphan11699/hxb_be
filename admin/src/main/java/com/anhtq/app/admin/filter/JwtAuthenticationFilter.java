package com.anhtq.app.admin.filter;

import com.anhtq.app.admin.service.userdetailservice.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  private final UserDetailServiceImpl userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String email;
    if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    try {
      email = jwtService.extractUsername(jwt);
      if (Objects.nonNull(email)
          && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
        UserDetails userDetails = userService.loadUserByUsername(email);

        if (jwtService.isValidToken(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken token =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(token);
        }
      }
    } catch (Exception e) {
      filterChain.doFilter(request, response);
    }

    filterChain.doFilter(request, response);
  }
}
