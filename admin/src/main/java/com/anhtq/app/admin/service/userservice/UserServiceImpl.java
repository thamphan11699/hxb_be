package com.anhtq.app.admin.service.userservice;

import com.anhtq.app.admin.controller.usercontroller.request.CreateUserPostServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.request.EditUserPostServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.request.UserGetServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.response.*;
import com.anhtq.app.admin.doamin.RoleEntity;
import com.anhtq.app.admin.doamin.UserEntity;
import com.anhtq.app.admin.doamin.UserRoleEntity;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.repository.RoleRepository;
import com.anhtq.app.admin.repository.UserRepository;
import com.anhtq.app.admin.repository.UserRoleRepository;
import com.anhtq.app.admin.service.common.CommonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final UserRoleRepository userRoleRepository;

  private final CommonService commonService;

  private final PasswordEncoder passwordEncoder;

  private final EntityManager entityManager;

  private static final String SPACE = " ";

  @Override
  public CreateUserGetServiceResponse createUserGet() {
    return CreateUserGetServiceResponse.builder()
        .roles(
            roleRepository.findAll().stream()
                .map(
                    e ->
                        CreateUserRoleGetServiceResponse.builder()
                            .roleId(e.getId())
                            .roleName(e.getName())
                            .build())
                .toList())
        .build();
  }

  @Override
  @Transactional
  public void createUserPost(CreateUserPostServiceRequest request) {
    commonService.validate(request);

    userRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            e -> {
              throw new ApiException(
                  "Email " + e.getEmail() + " already exits!", HttpStatus.CONFLICT);
            });
    roleRepository
        .findById(request.getRoleId())
        .orElseThrow(() -> new ApiException("Can not get ROLE", HttpStatus.NOT_FOUND));

    UserEntity user =
        UserEntity.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .status("02")
            .phoneNumber(request.getPhoneNumber())
            .avatar(request.getAvatar())
            .build();
    userRepository.save(user);

    userRoleRepository.save(
        UserRoleEntity.builder().userId(user.getId()).roleId(request.getRoleId()).build());
  }

  @Override
  public UserGetServiceResponse getUsers(UserGetServiceRequest request) {
    int pageSize = Objects.nonNull(request.getPageSize()) ? request.getPageSize() : 50;
    int pageIndex =
        Objects.nonNull(request.getPageIndex())
            ? request.getPageIndex() > 0 ? request.getPageIndex() : 1
            : 1;
    int offset = (pageIndex - 1) * pageSize;

    String orderBy = !ObjectUtils.isEmpty(request.getOrderBy()) ? request.getOrderBy() : "id";
    String orderType = Objects.nonNull(request.getOrderType()) ? request.getOrderType() : "";

    StringBuilder countSql = new StringBuilder("select count(DISTINCT entity.id) ");

    countSql.append(
        " FROM UserEntity AS entity INNER JOIN UserRoleEntity as ur ON entity.id = ur.userId INNER JOIN RoleEntity as r ON r.id = ur.roleId WHERE (1=1)");

    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.usercontroller.response.");
    sql.append(
        "UserItemGetServiceResponse( entity.id, entity.email, entity.firstName, entity.lastName, entity.phoneNumber, entity.status, entity.avatar, r.id) ");
    sql.append(
        " FROM UserEntity AS entity INNER JOIN UserRoleEntity as ur ON entity.id = ur.userId INNER JOIN RoleEntity as r ON r.id = ur.roleId WHERE (1=1)");
    if (!ObjectUtils.isEmpty(request.getSearchWord())) {
      sql.append(SPACE)
          .append(
              " AND (entity.email LIKE :word OR entity.firstName LIKE :word OR entity.lastName LIKE :word OR entity.phoneNumber LIKE :word)");
      countSql
          .append(SPACE)
          .append(
              " AND (entity.email LIKE :word OR entity.firstName LIKE :word OR entity.lastName LIKE :word OR entity.phoneNumber LIKE :word)");
    }
    sql.append(SPACE).append("ORDER BY").append(SPACE).append("entity.").append(orderBy);

    sql.append(SPACE).append(orderType);

    Query query = entityManager.createQuery(sql.toString());
    Query countQuery = entityManager.createQuery(countSql.toString());
    query.setFirstResult(offset);
    query.setMaxResults(pageSize);
    if (!ObjectUtils.isEmpty(request.getSearchWord())) {
      query.setParameter("word", '%' + request.getSearchWord() + '%');
      countQuery.setParameter("word", '%' + request.getSearchWord() + '%');
    }
    List<UserItemGetServiceResponse> results = query.getResultList();
    Long count = (Long) countQuery.getSingleResult();

    if (count == 0) {
      return UserGetServiceResponse.builder()
          .totalElements(count)
          .pageIndex(pageIndex)
          .items(Collections.emptyList())
          .build();
    }
    return UserGetServiceResponse.builder()
        .totalElements(count)
        .pageIndex(pageIndex)
        .items(results)
        .build();
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    UserEntity user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));

    user.setStatus("03");
    userRepository.save(user);
  }

  @Override
  public EditUserGetServiceResponse getUserById(Long id) {

    UserEntity user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));

    List<RoleEntity> role = roleRepository.findByUserId(id);

    if (CollectionUtils.isEmpty(role)) {
      throw new ApiException("Role not found.", HttpStatus.FORBIDDEN);
    }

    return EditUserGetServiceResponse.builder()
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .phoneNumber(user.getPhoneNumber())
        .avatar(user.getAvatar())
        .roleId(role.get(0).getId())
        .roles(
            roleRepository.findAll().stream()
                .map(
                    e ->
                        CreateUserRoleGetServiceResponse.builder()
                            .roleId(e.getId())
                            .roleName(e.getName())
                            .build())
                .toList())
        .build();
  }

  @Override
  public EditUserGetServiceResponse getUserByEmail(String email) {
    UserEntity user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));

    List<RoleEntity> role = roleRepository.findByUserId(user.getId());

    if (CollectionUtils.isEmpty(role)) {
      throw new ApiException("Role not found.", HttpStatus.FORBIDDEN);
    }

    return EditUserGetServiceResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .phoneNumber(user.getPhoneNumber())
        .avatar(user.getAvatar())
        .roleId(role.get(0).getId())
        .password(user.getPassword())
        .passwordConfirm(user.getPassword())
        .build();
  }

  @Override
  @Transactional
  public void editUserById(Long id, EditUserPostServiceRequest request) {

    commonService.validate(request);
    UserEntity user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("User not found.", HttpStatus.NOT_FOUND));

    roleRepository
        .findById(request.getRoleId())
        .orElseThrow(() -> new ApiException("Role not found.", HttpStatus.FORBIDDEN));

    List<UserRoleEntity> userRoles = userRoleRepository.findByUserId(id);

    if (CollectionUtils.isEmpty(userRoles)) {
      throw new ApiException("User role not found.", HttpStatus.FORBIDDEN);
    }

    user.setEmail(request.getEmail());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setAvatar(request.getAvatar());

    userRepository.save(user);

    UserRoleEntity userRole = userRoles.get(0);
    userRole.setUserId(id);
    userRole.setRoleId(request.getRoleId());
    userRoleRepository.save(userRole);
  }
}
