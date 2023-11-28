package com.anhtq.app.admin.service.discountservice;

import com.anhtq.app.admin.controller.customer.request.CheckDiscountRequest;
import com.anhtq.app.admin.controller.customer.response.CheckDiscountResponse;
import com.anhtq.app.admin.controller.discount.request.GetDiscountServiceRequest;
import com.anhtq.app.admin.controller.discount.request.InsertOrDiscountServiceRequest;
import com.anhtq.app.admin.controller.discount.response.GetDiscountDetailServiceResponse;
import com.anhtq.app.admin.controller.discount.response.GetDiscountsServiceResponse;
import com.anhtq.app.admin.doamin.DiscountEntity;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.repository.DiscountRepository;
import com.anhtq.app.admin.service.common.CommonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

  private static final DateTimeFormatter CUSTOM_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private static final String SPACE = " ";

  private final CommonService commonService;

  private final DiscountRepository discountRepository;

  private final EntityManager entityManager;

  @Override
  public void insertDiscount(InsertOrDiscountServiceRequest request) {

    commonService.validate(request);

    String code = UUID.randomUUID().toString().substring(0, 5).toUpperCase(Locale.ROOT);

    DiscountEntity discountEntity =
        DiscountEntity.builder()
            .code(code)
            .expiredAt(convertLongToLocalDateTime(request.getExpiredAt()))
            .deleteFlg(Boolean.FALSE)
            .minimumOrder(
                Objects.nonNull(request.getMinimumOrder()) ? request.getMinimumOrder() : 0L)
            .maximumMoney(
                Objects.nonNull(request.getMaximumMoney()) ? request.getMaximumMoney() : 0L)
            .percent(request.getPercent())
            .build();

    discountRepository.save(discountEntity);
  }

  @Override
  public CheckDiscountResponse checkDiscountCode(CheckDiscountRequest request) {

    commonService.validate(request);

    DiscountEntity discountEntity =
        discountRepository
            .findByCode(request.getCode())
            .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
            .orElseThrow(
                () ->
                    new ApiException(
                        "Khuyến mãi không tồn tại hoặc đã bị xóa.", HttpStatus.NOT_FOUND));

    if (discountEntity.getExpiredAt().isBefore(LocalDateTime.now())) {
      throw new ApiException("Khuyến mãi đã hết hạn.", HttpStatus.BAD_REQUEST);
    }

    long totalPrice = Objects.nonNull(request.getTotalPrice()) ? request.getTotalPrice() : 0L;

    if (totalPrice < discountEntity.getMinimumOrder()) {
      throw new ApiException("Khuyến mãi không thể áp dụng.", HttpStatus.BAD_REQUEST);
    }

    long discountMoney = 0L;

    discountMoney = request.getTotalPrice() * discountEntity.getPercent() / 100;
    if (discountMoney > discountEntity.getMaximumMoney()) {
      discountMoney = discountEntity.getMaximumMoney();
    }
    return CheckDiscountResponse.builder().id(discountEntity.getId()).price(discountMoney).build();
  }

  @Override
  public void updateDiscount(InsertOrDiscountServiceRequest request, Long id) {
    commonService.validate(request);

    DiscountEntity discountEntity =
        discountRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ApiException(
                        "Khuyến mãi không tồn tại hoặc đã bị xóa", HttpStatus.NOT_FOUND));

    discountEntity.setPercent(request.getPercent());
    discountEntity.setExpiredAt(convertLongToLocalDateTime(request.getExpiredAt()));
    discountEntity.setMaximumMoney(
        Objects.nonNull(request.getMaximumMoney()) ? request.getMaximumMoney() : 0L);
    discountEntity.setMinimumOrder(
        Objects.nonNull(request.getMinimumOrder()) ? request.getMinimumOrder() : 0L);

    discountRepository.save(discountEntity);
  }

  @Override
  public GetDiscountDetailServiceResponse findDiscountById(Long id) {
    DiscountEntity discount =
        discountRepository
            .findById(id)
            .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
            .orElseThrow(
                () ->
                    new ApiException(
                        "Khuyến mãi không tồn tại hoặc đã bị xóa", HttpStatus.NOT_FOUND));
    return GetDiscountDetailServiceResponse.builder()
        .expiredAtString(formatDate(discount.getExpiredAt()))
        .expiredAt(discount.getExpiredAt())
        .code(discount.getCode())
        .percent(discount.getPercent())
        .id(discount.getId())
        .maximumMoney(discount.getMaximumMoney())
        .minimumOrder(discount.getMinimumOrder())
        .build();
  }

  @Override
  public GetDiscountsServiceResponse getDiscounts(GetDiscountServiceRequest request) {
    int pageSize = Objects.nonNull(request.getPageSize()) ? request.getPageSize() : 50;
    int pageIndex =
        Objects.nonNull(request.getPageIndex())
            ? request.getPageIndex() > 0 ? request.getPageIndex() : 1
            : 1;
    int offset = (pageIndex - 1) * pageSize;

    String orderBy = !ObjectUtils.isEmpty(request.getOrderBy()) ? request.getOrderBy() : "id";
    String orderType = Objects.nonNull(request.getOrderType()) ? request.getOrderType() : "";

    StringBuilder countSql = new StringBuilder("select count(DISTINCT entity.id) ");

    countSql.append(" FROM DiscountEntity AS entity WHERE (1=1) AND entity.deleteFlg = FALSE");

    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.discount.response.");
    sql.append(
        "GetDiscountDetailServiceResponse(entity.id, entity.code, entity.percent, entity.expiredAt, entity.minimumOrder, entity.maximumMoney) ");
    sql.append(" FROM DiscountEntity AS entity WHERE (1=1) AND entity.deleteFlg = FALSE");
    if (!ObjectUtils.isEmpty(request.getSearchWord())) {
      sql.append(SPACE).append(" AND (entity.code LIKE :word)");
      countSql.append(SPACE).append(" AND (entity.code LIKE :word)");
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
    List<GetDiscountDetailServiceResponse> results = query.getResultList();
    Long count = (Long) countQuery.getSingleResult();

    if (count == 0) {
      return GetDiscountsServiceResponse.builder()
          .totalElements(count)
          .pageIndex(pageIndex)
          .items(Collections.emptyList())
          .build();
    }
    return GetDiscountsServiceResponse.builder()
        .totalElements(count)
        .pageIndex(pageIndex)
        .items(
            results.stream()
                .map(
                    e ->
                        GetDiscountDetailServiceResponse.builder()
                            .id(e.getId())
                            .percent(e.getPercent())
                            .expiredAt(e.getExpiredAt())
                            .expiredAtString(formatDate(e.getExpiredAt()))
                            .code(e.getCode())
                            .minimumOrder(e.getMinimumOrder())
                            .maximumMoney(e.getMaximumMoney())
                            .build())
                .toList())
        .build();
  }

  @Override
  public void deleteDiscount(Long id) {
    DiscountEntity discount =
        discountRepository
            .findById(id)
            .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
            .orElseThrow(
                () ->
                    new ApiException(
                        "Khuyến mãi không tồn tại hoặc đã bị xóa", HttpStatus.NOT_FOUND));

    discount.setDeleteFlg(Boolean.TRUE);

    discountRepository.save(discount);
  }

  private LocalDateTime convertLongToLocalDateTime(Long time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
  }

  private String formatDate(LocalDateTime time) {
    return time.format(CUSTOM_FORMATTER);
  }
}
