package com.anhtq.app.admin.controller.usercontroller;

import com.anhtq.app.admin.controller.authcontroller.serviceresponse.AuthenticationResponse;
import com.anhtq.app.admin.controller.usercontroller.request.CreateUserPostServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.request.EditUserPostServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.request.UserGetServiceRequest;
import com.anhtq.app.admin.controller.usercontroller.response.CreateUserGetServiceResponse;
import com.anhtq.app.admin.controller.usercontroller.response.EditUserGetServiceResponse;
import com.anhtq.app.admin.controller.usercontroller.response.UserGetServiceResponse;
import com.anhtq.app.admin.service.authservice.AuthenticationService;
import com.anhtq.app.admin.service.userservice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final AuthenticationService authenticationService;

  private final UserService userService;

  @GetMapping("/current-user")
  public AuthenticationResponse getCurrentUser() {
    return authenticationService.getCurrentUser();
  }

  @GetMapping("/create-user")
  public CreateUserGetServiceResponse createUserGet() {
    return userService.createUserGet();
  }

  @PostMapping("/create-user")
  public void createUserPost(@RequestBody CreateUserPostServiceRequest request) {
    userService.createUserPost(request);
  }

  @GetMapping("")
  public UserGetServiceResponse getUserPost(@ModelAttribute UserGetServiceRequest request) {
    return userService.getUsers(request);
  }

  @PostMapping("/delete/{id}")
  public void createUserPost(@PathVariable("id") Long id) {
    userService.deleteUser(id);
  }

  @GetMapping("/edit-user/{id}")
  public EditUserGetServiceResponse editUserGet(@PathVariable("id") Long id) {
    return userService.getUserById(id);
  }

  @PostMapping("/edit-user/{id}")
  public void editUserPost(
      @RequestBody EditUserPostServiceRequest request, @PathVariable("id") Long id) {
    userService.editUserById(id, request);
  }

  @GetMapping("/get-user-by-email/{email}")
  public EditUserGetServiceResponse getUserByEmail(@PathVariable("email") String email) {
    return userService.getUserByEmail(email);
  }
}
