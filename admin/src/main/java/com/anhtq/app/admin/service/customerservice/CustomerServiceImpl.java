package com.anhtq.app.admin.service.customerservice;

import com.anhtq.app.admin.controller.customer.request.CustomerGetServiceRequest;
import com.anhtq.app.admin.controller.customer.response.*;
import com.anhtq.app.admin.doamin.ServiceEntity;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.repository.ServiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private static final String SPACE = " ";

  private static final DateTimeFormatter CUSTOM_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final ServiceRepository serviceRepository;

  private final EntityManager entityManager;

  @Override
  public GetHomePageServiceResponse getHomePage() {
    String sql =
        "SELECT new com.anhtq.app.admin.controller.customer.response.GetHomePageServiceServiceResponse(entity.id, entity.name, entity.image, entity.price)"
            + SPACE
            + "FROM ServiceEntity AS entity INNER JOIN CategoriesEntity AS ca ON ca.id = entity.categoryId WHERE (1=1) AND entity.deleteFlg = FALSE"
            + SPACE
            + "ORDER BY entity.updateDate DESC";

    Query query = entityManager.createQuery(sql);
    query.setFirstResult(0);
    query.setMaxResults(10);

    List<GetHomePageServiceServiceResponse> result = query.getResultList();
    return GetHomePageServiceResponse.builder()
        .services(result)
        .slideUrls(Collections.emptyList())
        .build();
  }

  @Override
  public GetServicePageServiceResponse getServicePage(Integer start) {
    String sql =
        "SELECT new com.anhtq.app.admin.controller.customer.response.GetServicePageItemServiceResponse(entity.id, entity.name, entity.sortDescription, entity.image, entity.price, entity.updateDate)"
            + SPACE
            + "FROM ServiceEntity AS entity INNER JOIN CategoriesEntity AS ca ON ca.id = entity.categoryId WHERE (1=1) AND entity.deleteFlg = FALSE"
            + SPACE
            + "ORDER BY entity.updateDate DESC";

    String sqlCount =
        "SELECT COUNT(entity.id)"
            + SPACE
            + "FROM ServiceEntity AS entity INNER JOIN CategoriesEntity AS ca ON ca.id = entity.categoryId WHERE (1=1) AND entity.deleteFlg = FALSE";

    Query query = entityManager.createQuery(sql);
    Query countQuery = entityManager.createQuery(sqlCount);

    query.setFirstResult(Objects.isNull(start) ? 0 : start);
    query.setMaxResults(20);

    Long count = (Long) countQuery.getSingleResult();

    List<GetServicePageItemServiceResponse> result = query.getResultList();
    return GetServicePageServiceResponse.builder()
        .count(count)
        .services(
            result.stream()
                .map(
                    e ->
                        GetServicePageItemServiceResponse.builder()
                            .name(e.getName())
                            .id(e.getId())
                            .image(e.getImage())
                            .price(e.getPrice())
                            .lastUpdated(formatDate(e.getLastUpdate()))
                            .sortDescription(e.getSortDescription())
                            .build())
                .toList())
        .build();
  }

  @Override
  public GetServiceDetailPageServiceResponse getServiceDetailPage(Long id) {
    ServiceEntity entity =
        serviceRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Dịch vụ không tồn tại.", HttpStatus.NOT_FOUND));
    return GetServiceDetailPageServiceResponse.builder()
        .name(entity.getName())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .introduction(entity.getIntroduction())
        .build();
  }

  @Override
  public CustomerGetServiceResponse getCustomers(CustomerGetServiceRequest request) {
    int pageSize = Objects.nonNull(request.getPageSize()) ? request.getPageSize() : 50;
    int pageIndex =
        Objects.nonNull(request.getPageIndex())
            ? request.getPageIndex() > 0 ? request.getPageIndex() : 1
            : 1;
    int offset = (pageIndex - 1) * pageSize;

    String orderBy = !ObjectUtils.isEmpty(request.getOrderBy()) ? request.getOrderBy() : "id";
    String orderType = Objects.nonNull(request.getOrderType()) ? request.getOrderType() : "";

    StringBuilder countSql = new StringBuilder("select count(DISTINCT entity.id) ");

    countSql.append(" FROM CustomerEntity AS entity WHERE (1=1) ");

    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.customer.response.");
    sql.append(
        "CustomerItemGetServiceResponse(entity.id, entity.firstName, entity.lastName, entity.email, entity.phoneNumber) ");
    sql.append(" FROM CustomerEntity AS entity WHERE (1=1) ");
    if (!ObjectUtils.isEmpty(request.getSearchWord())) {
      sql.append(SPACE)
          .append(
              " AND (entity.lastName LIKE :word OR entity.firstName LIKE :word OR entity.email LIKE :word)");
      countSql
          .append(SPACE)
          .append(
              " AND (entity.lastName LIKE :word OR entity.firstName LIKE :word OR entity.email LIKE :word)");
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
    List<CustomerItemGetServiceResponse> results = query.getResultList();
    Long count = (Long) countQuery.getSingleResult();

    if (count == 0) {
      return CustomerGetServiceResponse.builder()
          .totalElements(count)
          .pageIndex(pageIndex)
          .items(Collections.emptyList())
          .build();
    }
    return CustomerGetServiceResponse.builder()
        .totalElements(count)
        .pageIndex(pageIndex)
        .items(results)
        .build();
  }

  private String formatDate(LocalDateTime time) {
    return time.format(CUSTOM_FORMATTER);
  }
}
