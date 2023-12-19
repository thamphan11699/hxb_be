package com.anhtq.app.admin.service.catategories;

import com.anhtq.app.admin.controller.category.servicerequest.CategoriesGetServiceRequest;
import com.anhtq.app.admin.controller.category.servicerequest.CreateCategoryPostServiceRequest;
import com.anhtq.app.admin.controller.category.servicerequest.EditCategoryPostServiceRequest;
import com.anhtq.app.admin.controller.category.serviceresponse.CategoriesGetServiceResponse;
import com.anhtq.app.admin.controller.category.serviceresponse.CategoryItemGetServiceResponse;
import com.anhtq.app.admin.controller.category.serviceresponse.EditCategoryGetServiceResponse;
import com.anhtq.app.admin.doamin.CategoriesEntity;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.repository.CategoriesRepository;
import com.anhtq.app.admin.service.common.CommonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

  private static final String SPACE = " ";

  private final CategoriesRepository categoriesRepository;

  private final CommonService commonService;

  private final EntityManager entityManager;

  @Override
  public void creteCategoryPost(CreateCategoryPostServiceRequest request) {

    commonService.validate(request);

    categoriesRepository
        .findByName(request.getName())
        .ifPresent(
            e -> {
              throw new ApiException(
                  "Tên danh mục " + e.getName() + " đã tồn tại.", HttpStatus.CONFLICT);
            });

    CategoriesEntity categoriesEntity =
        CategoriesEntity.builder()
            .name(request.getName())
            .deleteFlg(Boolean.FALSE)
            .isHome(request.getIsHome())
            .build();

    categoriesRepository.save(categoriesEntity);
  }

  @Override
  public CategoriesGetServiceResponse getCategories(CategoriesGetServiceRequest request) {
    int pageSize = Objects.nonNull(request.getPageSize()) ? request.getPageSize() : 50;
    int pageIndex =
        Objects.nonNull(request.getPageIndex())
            ? request.getPageIndex() > 0 ? request.getPageIndex() : 1
            : 1;
    int offset = (pageIndex - 1) * pageSize;

    String orderBy = !ObjectUtils.isEmpty(request.getOrderBy()) ? request.getOrderBy() : "id";
    String orderType = Objects.nonNull(request.getOrderType()) ? request.getOrderType() : "";

    StringBuilder countSql = new StringBuilder("select count(DISTINCT entity.id) ");

    countSql.append(" FROM CategoriesEntity AS entity WHERE (1=1) AND entity.deleteFlg = FALSE");

    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.category.serviceresponse.");
    sql.append("CategoryItemGetServiceResponse( entity.id, entity.name, entity.isHome) ");
    sql.append(" FROM CategoriesEntity AS entity WHERE (1=1) AND entity.deleteFlg = FALSE");
    if (!ObjectUtils.isEmpty(request.getSearchWord())) {
      sql.append(SPACE).append(" AND (entity.name LIKE :word)");
      countSql.append(SPACE).append(" AND (entity.name LIKE :word)");
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
    List<CategoryItemGetServiceResponse> results = query.getResultList();
    Long count = (Long) countQuery.getSingleResult();

    if (count == 0) {
      return CategoriesGetServiceResponse.builder()
          .totalElements(count)
          .pageIndex(pageIndex)
          .items(Collections.emptyList())
          .build();
    }
    return CategoriesGetServiceResponse.builder()
        .totalElements(count)
        .pageIndex(pageIndex)
        .items(results)
        .build();
  }

  @Override
  public void deleteCategory(Long id) {
    CategoriesEntity entity =
        categoriesRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Không tìm thấy danh mục.", HttpStatus.NOT_FOUND));

    entity.setDeleteFlg(Boolean.TRUE);

    categoriesRepository.save(entity);
  }

  @Override
  public EditCategoryGetServiceResponse getCategoryById(Long id) {
    CategoriesEntity entity =
        categoriesRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Không tìm thấy danh mục.", HttpStatus.NOT_FOUND));
    return EditCategoryGetServiceResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .isHome(entity.getIsHome())
        .build();
  }

  @Override
  public void editCategoryById(Long id, EditCategoryPostServiceRequest request) {
    commonService.validate(request);

    CategoriesEntity entity =
        categoriesRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Không tìm thấy danh mục.", HttpStatus.NOT_FOUND));

    if (Objects.equals(entity.getName(), request.getName())
        && Objects.equals(entity.getIsHome(), request.getIsHome())) {
      categoriesRepository
          .findByName(request.getName())
          .ifPresent(
              e -> {
                throw new ApiException(
                    "Tên danh mục " + e.getName() + " đã tồn tại.", HttpStatus.CONFLICT);
              });
    }

    entity.setName(request.getName());
    entity.setIsHome(request.getIsHome());

    categoriesRepository.save(entity);
  }
}
