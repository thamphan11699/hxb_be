package com.anhtq.app.admin.service.userservice;

import com.anhtq.app.admin.controller.usercontroller.request.CreateUserPostServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.request.EditUserPostServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.request.UserGetServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.response.CreateUserGetServiceResponse;
import com.anhtq.app.admin.controller.usercontroller.response.EditUserGetServiceResponse;
import com.anhtq.app.admin.controller.usercontroller.response.UserGetServiceResponse;

public interface UserService {

  CreateUserGetServiceResponse createUserGet();

  void createUserPost(CreateUserPostServiceRequest request);

  UserGetServiceResponse getUsers(UserGetServiceRequest request);

  void deleteUser(Long id);

  EditUserGetServiceResponse getUserById(Long id);

  EditUserGetServiceResponse getUserByEmail(String email);

  void editUserById(Long id, EditUserPostServiceRequest request);
}
