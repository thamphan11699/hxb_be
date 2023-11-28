package com.anhtq.app.admin.controller.category;

import com.anhtq.app.admin.controller.category.servicerequest.CategoriesGetServiceRequest;
import com.anhtq.app.admin.controller.category.servicerequest.CreateCategoryPostServiceRequest;
import com.anhtq.app.admin.controller.category.servicerequest.EditCategoryPostServiceRequest;
import com.anhtq.app.admin.controller.category.serviceresponse.CategoriesGetServiceResponse;
import com.anhtq.app.admin.controller.category.serviceresponse.EditCategoryGetServiceResponse;
import com.anhtq.app.admin.service.catategories.CategoriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

  private final CategoriesService categoriesService;

  @Secured({ "ROLE_ADMIN" })
  @PostMapping("/create")
  public void createCategoryPost(@RequestBody CreateCategoryPostServiceRequest request) {
    categoriesService.creteCategoryPost(request);
  }

  @Secured({ "ROLE_ADMIN" })
  @GetMapping("")
  public CategoriesGetServiceResponse getCategories(
      @ModelAttribute CategoriesGetServiceRequest request) {
    return categoriesService.getCategories(request);
  }

  @Secured({ "ROLE_ADMIN" })
  @PostMapping("/delete/{id}")
  public void deleteCategoryPost(@PathVariable("id") Long id) {
    categoriesService.deleteCategory(id);
  }

  @Secured({ "ROLE_ADMIN" })
  @GetMapping("/edit/{id}")
  public EditCategoryGetServiceResponse editCategoryGet(@PathVariable("id") Long id) {
    return categoriesService.getCategoryById(id);
  }

  @Secured({ "ROLE_ADMIN" })
  @PostMapping("/edit/{id}")
  public void editCategoryPost(
      @RequestBody EditCategoryPostServiceRequest request, @PathVariable("id") Long id) {
    categoriesService.editCategoryById(id, request);
  }
}
