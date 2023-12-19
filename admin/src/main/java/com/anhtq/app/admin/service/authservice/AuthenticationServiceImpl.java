package com.anhtq.app.admin.service.authservice;

import com.anhtq.app.admin.controller.authcontroller.servicerequest.LogInServiceRequest;
import com.anhtq.app.admin.controller.authcontroller.servicerequest.RegisterServiceRequest;
import com.anhtq.app.admin.controller.authcontroller.serviceresponse.AuthenticationResponse;
import com.anhtq.app.admin.controller.authcontroller.serviceresponse.AuthenticationUserResponse;
import com.anhtq.app.admin.doamin.RoleEntity;
import com.anhtq.app.admin.doamin.UserEntity;
import com.anhtq.app.admin.doamin.UserRoleEntity;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.filter.JwtService;
import com.anhtq.app.admin.repository.RoleRepository;
import com.anhtq.app.admin.repository.UserRepository;
import com.anhtq.app.admin.repository.UserRoleRepository;
import com.anhtq.app.admin.service.common.CommonService;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final CommonService commonService;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

  private final PasswordEncoder passwordEncoder;

  private final UserRoleRepository userRoleRepository;

  @Override
  @Transactional
  public AuthenticationResponse authenticate(LogInServiceRequest request) {

    commonService.validate(request);

    try {
      UserEntity user =
          userRepository
              .findByEmail(request.getEmail())
              .orElseThrow(
                  () ->
                      new ApiException(
                          "User name không tồn tại trong hệ thống.", HttpStatus.UNAUTHORIZED));

      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

      if (!authentication.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .toList()
          .contains("ROLE_ADMIN")) {
        throw new ApiException(
            "Tài khoản của bạn không có quyền truy cập hệ thống.", HttpStatus.UNAUTHORIZED);
      }

      String token =
          jwtService.generateToken(
              Map.of(),
              org.springframework.security.core.userdetails.User.builder()
                  .username(request.getEmail())
                  .authorities(authentication.getAuthorities())
                  .password(request.getPassword())
                  .build());
      return AuthenticationResponse.builder()
          .token(token)
          .user(
              AuthenticationUserResponse.builder()
                  .id(user.getId())
                  .email(authentication.getName())
                  .avatar(user.getAvatar())
                  .roles(
                      authentication.getAuthorities().stream()
                          .map(GrantedAuthority::getAuthority)
                          .toList())
                  .build())
          .build();

    } catch (Exception e) {
      throw new ApiException("Mật khẩu không đúng.", HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public AuthenticationResponse authenticateCustomer(LogInServiceRequest request) {
    commonService.validate(request);

    try {
      UserEntity user =
          userRepository
              .findByEmail(request.getEmail())
              .orElseThrow(
                  () ->
                      new ApiException(
                          "User name không tồn tại trong hệ thống.", HttpStatus.UNAUTHORIZED));

      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

      if (!authentication.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .toList()
          .contains("ROLE_CUSTOMER")) {
        throw new ApiException(
            "Tài khoản của bạn không có quyền truy cập hệ thống.", HttpStatus.UNAUTHORIZED);
      }

      String token =
          jwtService.generateToken(
              Map.of(),
              org.springframework.security.core.userdetails.User.builder()
                  .username(request.getEmail())
                  .authorities(authentication.getAuthorities())
                  .password(request.getPassword())
                  .build());
      return AuthenticationResponse.builder()
          .token(token)
          .user(
              AuthenticationUserResponse.builder()
                  .id(user.getId())
                  .email(authentication.getName())
                  .avatar(user.getAvatar())
                  .roles(
                      authentication.getAuthorities().stream()
                          .map(GrantedAuthority::getAuthority)
                          .toList())
                  .build())
          .build();

    } catch (Exception e) {
      throw new ApiException("Mật khẩu không đúng.", HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  @Transactional
  public AuthenticationResponse register(RegisterServiceRequest request) {
    commonService.validate(request);

    userRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            e -> {
              throw new ApiException(
                  "Email " + e.getEmail() + " already exit", HttpStatus.CONFLICT);
            });

    List<RoleEntity> roles = roleRepository.findByIdIn(request.getRoleIds());
    if (CollectionUtils.isEmpty(roles)) {
      throw new ApiException("Can not get ROLE from IDS", HttpStatus.NOT_FOUND);
    }

    UserEntity user =
        UserEntity.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .status("02")
            .phoneNumber(request.getPhoneNumber())
            .avatar(request.getImage())
            .dateOfBirth(
                Objects.nonNull(request.getDateOfBirth())
                    ? convertLongToLocalDateTime(request.getDateOfBirth())
                    : null)
            .gender(request.getGender())
            .build();
    userRepository.save(user);

    roles.forEach(
        r -> {
          UserRoleEntity userRole =
              UserRoleEntity.builder().userId(user.getId()).roleId(r.getId()).build();
          userRoleRepository.save(userRole);
        });

    List<SimpleGrantedAuthority> authorities =
        roles.stream().map(e -> new SimpleGrantedAuthority(e.getName())).toList();
    String token =
        jwtService.generateToken(
            new HashMap<>(),
            org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .authorities(authorities)
                .password(user.getPassword())
                .build());
    return AuthenticationResponse.builder()
        .token(token)
        .user(
            AuthenticationUserResponse.builder()
                .id(user.getId())
                .roles(authorities.stream().map(SimpleGrantedAuthority::getAuthority).toList())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build())
        .build();
  }

  @Override
  public AuthenticationResponse getCurrentUser() {
    try {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (Objects.isNull(user)) {
        throw new ApiException("UnAuthorized", HttpStatus.UNAUTHORIZED);
      }
      String username = user.getUsername();
      UserEntity userEntity =
          userRepository
              .findByEmail(username)
              .orElseThrow(
                  () ->
                      new ApiException(
                          "User " + username + " not found in database", HttpStatus.UNAUTHORIZED));

      List<RoleEntity> roleEntities = roleRepository.findByUserId(userEntity.getId());
      return AuthenticationResponse.builder()
          .user(
              AuthenticationUserResponse.builder()
                  .id(userEntity.getId())
                  .roles(roleEntities.stream().map(RoleEntity::getName).toList())
                  .email(userEntity.getEmail())
                  .avatar(userEntity.getAvatar())
                  .build())
          .build();
    } catch (Exception e) {
      throw new ApiException("UnAuthorized", HttpStatus.UNAUTHORIZED);
    }
  }

  private LocalDateTime convertLongToLocalDateTime(Long time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
  }
}
