package com.anhtq.app.admin.service.service;

import com.anhtq.app.admin.controller.service.servicerequest.CreateServicePostServiceRequest;
import com.anhtq.app.admin.controller.service.servicerequest.EditServicePostServiceRequest;
import com.anhtq.app.admin.controller.service.servicerequest.ServiceGetServiceRequest;
import com.anhtq.app.admin.controller.service.serviceresponse.*;
import com.anhtq.app.admin.doamin.CategoriesEntity;
import com.anhtq.app.admin.doamin.ServiceEntity;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.repository.CategoriesRepository;
import com.anhtq.app.admin.repository.ServiceRepository;
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
public class ServiceServiceImpl implements ServiceService {

  private final CommonService commonService;

  private final EntityManager entityManager;

  private final ServiceRepository serviceRepository;

  private final CategoriesRepository categoriesRepository;

  private static final String SPACE = " ";

  @Override
  public CreateServiceGetServiceResponse createServiceGet() {
    List<CategoriesEntity> categoriesEntities = categoriesRepository.findAll();
    return CreateServiceGetServiceResponse.builder()
        .categories(
            categoriesEntities.stream()
                .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
                .map(
                    e ->
                        CreateServiceCategoryGetServiceResponse.builder()
                            .label(e.getName())
                            .value(e.getId())
                            .build())
                .toList())
        .build();
  }

  @Override
  public void createServicePost(CreateServicePostServiceRequest request) {
    commonService.validate(request);

    serviceRepository
        .findByName(request.getName())
        .ifPresent(
            e -> {
              throw new ApiException(
                  "Name " + e.getName() + " already exits!", HttpStatus.CONFLICT);
            });

    ServiceEntity entity =
        ServiceEntity.builder()
            .name(request.getName())
            .deleteFlg(Boolean.FALSE)
            .image(request.getImage())
            .sortDescription(request.getSortDescription())
            .description(request.getDescription())
            .categoryId(request.getCategoryId())
            .price(request.getPrice())
            .introduction(request.getIntroduction())
            .build();

    serviceRepository.save(entity);
  }

  @Override
  public ServiceGetServiceResponse getServices(ServiceGetServiceRequest request) {
    int pageSize = Objects.nonNull(request.getPageSize()) ? request.getPageSize() : 50;
    int pageIndex =
        Objects.nonNull(request.getPageIndex())
            ? request.getPageIndex() > 0 ? request.getPageIndex() : 1
            : 1;
    int offset = (pageIndex - 1) * pageSize;

    String orderBy = !ObjectUtils.isEmpty(request.getOrderBy()) ? request.getOrderBy() : "id";
    String orderType = Objects.nonNull(request.getOrderType()) ? request.getOrderType() : "DESC";

    StringBuilder countSql = new StringBuilder("select count(DISTINCT entity.id) ");

    countSql.append(
        " FROM ServiceEntity AS entity INNER JOIN CategoriesEntity AS ca ON ca.id = entity.categoryId WHERE (1=1) AND entity.deleteFlg = FALSE");

    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.service.serviceresponse.");
    sql.append(
        "ServiceItemGetServiceResponse( entity.id, entity.name, entity.sortDescription, entity.description, entity.image ) ");
    sql.append(
        " FROM ServiceEntity AS entity INNER JOIN CategoriesEntity AS ca ON ca.id = entity.categoryId WHERE (1=1) AND entity.deleteFlg = FALSE");
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
    List<ServiceItemGetServiceResponse> results = query.getResultList();
    Long count = (Long) countQuery.getSingleResult();

    if (count == 0) {
      return ServiceGetServiceResponse.builder()
          .totalElements(count)
          .pageIndex(pageIndex)
          .items(Collections.emptyList())
          .build();
    }
    return ServiceGetServiceResponse.builder()
        .totalElements(count)
        .pageIndex(pageIndex)
        .items(results)
        .build();
  }

  @Override
  public void deleteService(Long id) {
    ServiceEntity entity =
        serviceRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Service not found.", HttpStatus.NOT_FOUND));

    entity.setDeleteFlg(Boolean.TRUE);

    serviceRepository.save(entity);
  }

  @Override
  public EditServiceGetServiceResponse getServiceById(Long id) {
    ServiceEntity entity =
        serviceRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Service not found.", HttpStatus.NOT_FOUND));

    List<CategoriesEntity> categoriesEntities = categoriesRepository.findAll();
    return EditServiceGetServiceResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .sortDescription(entity.getSortDescription())
        .description(entity.getDescription())
        .image(entity.getImage())
        .categoryId(entity.getCategoryId())
        .price(entity.getPrice())
        .introduction(entity.getIntroduction())
        .categories(
            categoriesEntities.stream()
                .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
                .map(
                    e ->
                        CreateServiceCategoryGetServiceResponse.builder()
                            .label(e.getName())
                            .value(e.getId())
                            .build())
                .toList())
        .build();
  }

  @Override
  public void editServiceById(Long id, EditServicePostServiceRequest request) {

    ServiceEntity entity =
        serviceRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Service not found.", HttpStatus.NOT_FOUND));

    entity.setName(request.getName());
    entity.setSortDescription(request.getSortDescription());
    entity.setDescription(request.getDescription());
    entity.setImage(request.getImage());
    entity.setCategoryId(request.getCategoryId());
    entity.setPrice(request.getPrice());
    entity.setIntroduction(request.getIntroduction());

    serviceRepository.save(entity);
  }
}
