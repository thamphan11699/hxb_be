package com.anhtq.app.admin.service.catategories;

import com.anhtq.app.admin.controller.category.servicerequest.CategoriesGetServiceRequest;
import com.anhtq.app.admin.controller.category.servicerequest.CreateCategoryPostServiceRequest;
import com.anhtq.app.admin.controller.category.servicerequest.EditCategoryPostServiceRequest;
import com.anhtq.app.admin.controller.category.serviceresponse.CategoriesGetServiceResponse;
import com.anhtq.app.admin.controller.category.serviceresponse.EditCategoryGetServiceResponse;

public interface CategoriesService {
//    CreateUserGetServiceResponse creteCategoryGet();

    void creteCategoryPost(CreateCategoryPostServiceRequest request);

    CategoriesGetServiceResponse getCategories(CategoriesGetServiceRequest request);

    void deleteCategory(Long id);

    EditCategoryGetServiceResponse getCategoryById(Long id);

    void editCategoryById(Long id, EditCategoryPostServiceRequest request);
}
