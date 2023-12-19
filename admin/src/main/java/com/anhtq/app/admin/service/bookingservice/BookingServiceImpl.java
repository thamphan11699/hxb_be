package com.anhtq.app.admin.service.bookingservice;

import com.anhtq.app.admin.controller.booking.servicerequest.CreateBookingServiceRequest;
import com.anhtq.app.admin.controller.booking.servicerequest.GetBookingServiceRequest;
import com.anhtq.app.admin.controller.booking.servicerequest.UpdateStatusRequest;
import com.anhtq.app.admin.controller.booking.serviceresponse.CreateBookingGetServiceResponse;
import com.anhtq.app.admin.controller.booking.serviceresponse.GetBookingDetailServiceResponse;
import com.anhtq.app.admin.controller.booking.serviceresponse.GetBookingServiceResponse;
import com.anhtq.app.admin.controller.booking.serviceresponse.GetMyBookingServiceResponse;
import com.anhtq.app.admin.doamin.*;
import com.anhtq.app.admin.excetion.ApiException;
import com.anhtq.app.admin.repository.*;
import com.anhtq.app.admin.service.common.CommonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

  private static final DateTimeFormatter CUSTOM_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  private static final String SPACE = " ";

  private final CustomerRepository customerRepository;

  private final BookingRepository bookingRepository;

  private final CommonService commonService;

  private final ServiceRepository serviceRepository;

  private final DiscountRepository discountRepository;

  private final BookingDetailRepository bookingDetailRepository;

  private final EntityManager entityManager;

  @Override
  public void booking(CreateBookingServiceRequest request) {
    commonService.validate(request);

    if (convertLongToLocalDateTime(request.getBookingDate()).isBefore(LocalDateTime.now())) {
      throw new ApiException("Thời gian đặt lịch phải lớn hơn hiện tại.", HttpStatus.BAD_REQUEST);
    }

    ServiceEntity serviceEntity =
        serviceRepository
            .findById(request.getServiceId())
            .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
            .orElseThrow(
                () ->
                    new ApiException(
                        "Mã dịch vụ không được tồn tại hoặc đã bị xóa khỏi hệ thống.",
                        HttpStatus.NOT_FOUND));

    Long discountMoney = 0L;
    if (Objects.nonNull(request.getDiscountId())) {
      DiscountEntity discountEntity =
          discountRepository
              .findById(request.getDiscountId())
              .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
              .orElseThrow(
                  () ->
                      new ApiException(
                          "Mã khuyến mãi đã bị xóa hoặc không tồn tại.", HttpStatus.NOT_FOUND));
      discountMoney = serviceEntity.getPrice() * discountEntity.getPercent() / 100;
      if (discountMoney > discountEntity.getMaximumMoney()) {
        discountMoney = discountEntity.getMaximumMoney();
      }
    }

    if (Objects.equals("1", request.getUserType())) {
      if (Objects.isNull(request.getUserId())) {
        throw new ApiException(
            "Vui lòng đăng nhập để thực hiện chức năng.", HttpStatus.BAD_REQUEST);
      }
      BookingEntity booking =
          BookingEntity.builder()
              .bookingDate(convertLongToLocalDateTime(request.getBookingDate()))
              .serviceId(request.getServiceId())
              .status("01")
              .userType("1")
              .userId(request.getUserId())
              .build();

      bookingRepository.save(booking);

      BookingDetailEntity bookingDetailEntity =
          BookingDetailEntity.builder()
              .bookingId(booking.getId())
              .discountId(request.getDiscountId())
              .cost(serviceEntity.getPrice())
              .promotionalPrice(serviceEntity.getPrice() - discountMoney)
              .build();

      bookingDetailRepository.save(bookingDetailEntity);
      return;
    }

    if (validateCustomer(
        request.getEmail(),
        request.getFirstName(),
        request.getLastName(),
        request.getPhoneNumber())) {
      throw new ApiException("Dữ liệu không đúng định dạng.", HttpStatus.BAD_REQUEST);
    }

    Long finalDiscountMoney = discountMoney;
    customerRepository
        .findByEmail(request.getEmail())
        .ifPresentOrElse(
            e -> {
              e.setFirstName(request.getFirstName());
              e.setLastName(request.getLastName());
              e.setPhoneNumber(request.getPhoneNumber());
              customerRepository.save(e);

              BookingEntity booking =
                  BookingEntity.builder()
                      .bookingDate(convertLongToLocalDateTime(request.getBookingDate()))
                      .serviceId(request.getServiceId())
                      .status("01")
                      .userType("2")
                      .customerId(e.getId())
                      .build();

              bookingRepository.save(booking);

              BookingDetailEntity bookingDetailEntity =
                  BookingDetailEntity.builder()
                      .bookingId(booking.getId())
                      .discountId(request.getDiscountId())
                      .cost(serviceEntity.getPrice())
                      .promotionalPrice(serviceEntity.getPrice() - finalDiscountMoney)
                      .build();

              bookingDetailRepository.save(bookingDetailEntity);
            },
            () -> {
              CustomerEntity customerEntity =
                  CustomerEntity.builder()
                      .email(request.getEmail())
                      .firstName(request.getFirstName())
                      .lastName(request.getLastName())
                      .phoneNumber(request.getPhoneNumber())
                      .build();
              customerRepository.save(customerEntity);

              BookingEntity booking =
                  BookingEntity.builder()
                      .bookingDate(convertLongToLocalDateTime(request.getBookingDate()))
                      .serviceId(request.getServiceId())
                      .status("01")
                      .userType("2")
                      .customerId(customerEntity.getId())
                      .build();

              bookingRepository.save(booking);

              BookingDetailEntity bookingDetailEntity =
                  BookingDetailEntity.builder()
                      .bookingId(booking.getId())
                      .discountId(request.getDiscountId())
                      .cost(serviceEntity.getPrice())
                      .promotionalPrice(serviceEntity.getPrice() - finalDiscountMoney)
                      .build();

              bookingDetailRepository.save(bookingDetailEntity);
            });
  }

  @Override
  public List<CreateBookingGetServiceResponse> getBooking() {
    return serviceRepository.findAll().stream()
        .filter(e -> Boolean.FALSE.equals(e.getDeleteFlg()))
        .map(
            e ->
                CreateBookingGetServiceResponse.builder()
                    .value(e.getId())
                    .label(e.getName())
                    .price(e.getPrice())
                    .build())
        .toList();
  }

  @Override
  public GetBookingServiceResponse getBookings(GetBookingServiceRequest request) {
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
        " FROM BookingEntity AS entity INNER JOIN BookingDetailEntity AS entityDetail ");
    countSql.append(SPACE).append("ON entity.id = entityDetail.bookingId");
    countSql
        .append(SPACE)
        .append("INNER JOIN ServiceEntity AS service ON service.id = entity.serviceId ");
    countSql.append(SPACE).append("LEFT JOIN UserEntity AS user ON user.id = entity.userId ");
    countSql
        .append(SPACE)
        .append("LEFT JOIN CustomerEntity AS customer ON customer.id = entity.customerId");
    countSql
        .append(SPACE)
        .append("LEFT JOIN DiscountEntity AS discount ON discount.id = entityDetail.discountId ");
    countSql.append(SPACE).append("WHERE (1=1) ");

    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.booking.serviceresponse.");
    sql.append(
        "GetBookingDetailServiceResponse(entity.id, entity.userType, customer.lastName, "
            + "user.lastName, service.name, entity.status, entity.bookingDate, discount.code, "
            + "entityDetail.cost, entityDetail.promotionalPrice, customer.firstName, user.firstName, customer.phoneNumber,"
            + "user.phoneNumber) ");
    sql.append(" FROM BookingEntity AS entity INNER JOIN BookingDetailEntity AS entityDetail ");
    sql.append(SPACE).append("ON entity.id = entityDetail.bookingId");
    sql.append(SPACE)
        .append("INNER JOIN ServiceEntity AS service ON service.id = entity.serviceId ");
    sql.append(SPACE).append("LEFT JOIN UserEntity AS user ON user.id = entity.userId ");
    sql.append(SPACE)
        .append("LEFT JOIN CustomerEntity AS customer ON customer.id = entity.customerId");
    sql.append(SPACE)
        .append("LEFT JOIN DiscountEntity AS discount ON discount.id = entityDetail.discountId ");
    sql.append(SPACE).append("WHERE (1=1) ");
    if (!ObjectUtils.isEmpty(request.getSearchWord())) {
      sql.append(SPACE)
          .append(" AND (user.phoneNumber LIKE :word OR customer.phoneNumber LIKE :word)");
      countSql
          .append(SPACE)
          .append(" AND (user.phoneNumber LIKE :word OR customer.phoneNumber LIKE :word)");
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
    List<GetBookingDetailServiceResponse> results = query.getResultList();
    Long count = (Long) countQuery.getSingleResult();

    if (count == 0) {
      return GetBookingServiceResponse.builder()
          .totalElements(count)
          .pageIndex(pageIndex)
          .items(Collections.emptyList())
          .build();
    }
    return GetBookingServiceResponse.builder()
        .totalElements(count)
        .pageIndex(pageIndex)
        .items(
            results.stream()
                .map(
                    e ->
                        GetBookingDetailServiceResponse.builder()
                            .id(e.getId())
                            .userType(e.getUserType())
                            .customerLastName(e.getCustomerLastName())
                            .userLastName(e.getUserLastName())
                            .serviceName(e.getServiceName())
                            .status(e.getStatus())
                            .bookingDate(e.getBookingDate())
                            .bookingDateString(formatDate(e.getBookingDate()))
                            .discountName(e.getDiscountName())
                            .cost(e.getCost())
                            .promotionalPrice(e.getPromotionalPrice())
                            .customerFirstName(e.getCustomerFirstName())
                            .userFirstName(e.getUserFirstName())
                            .customerPhoneNumber(e.getCustomerPhoneNumber())
                            .userPhoneNumber(e.getUserPhoneNumber())
                            .build())
                .toList())
        .build();
  }

  @Override
  public GetBookingDetailServiceResponse getBookingById(Long id) {
    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.booking.serviceresponse.");
    sql.append(
        "GetBookingDetailServiceResponse(entity.id, entity.userType, customer.lastName, "
            + "user.lastName, service.name, entity.status, entity.bookingDate, discount.code, "
            + "entityDetail.cost, entityDetail.promotionalPrice, customer.firstName, user.firstName, customer.phoneNumber, user.phoneNumber) ");
    sql.append(" FROM BookingEntity AS entity INNER JOIN BookingDetailEntity AS entityDetail ");
    sql.append(SPACE).append("ON entity.id = entityDetail.bookingId");
    sql.append(SPACE)
        .append("INNER JOIN ServiceEntity AS service ON service.id = entity.serviceId ");
    sql.append(SPACE).append("LEFT JOIN UserEntity AS user ON user.id = entity.userId ");
    sql.append(SPACE)
        .append("LEFT JOIN CustomerEntity AS customer ON customer.id = entity.customerId");
    sql.append(SPACE)
        .append("LEFT JOIN DiscountEntity AS discount ON discount.id = entityDetail.discountId ");
    sql.append(SPACE).append("WHERE entity.id = :id");
    Query query = entityManager.createQuery(sql.toString());
    query.setParameter("id", id);

    GetBookingDetailServiceResponse response =
        (GetBookingDetailServiceResponse) query.getSingleResult();
    response.setBookingDateString(formatDate(response.getBookingDate()));
    return response;
  }

  @Override
  public void updateStatus(UpdateStatusRequest request, Long id) {
    BookingEntity booking =
        bookingRepository
            .findById(id)
            .orElseThrow(() -> new ApiException("Đơn hàng không tồn tại.", HttpStatus.NOT_FOUND));

    booking.setStatus(request.getStatus());

    bookingRepository.save(booking);
  }

  @Override
  public List<GetMyBookingServiceResponse> getBookingByUserId(Long userId) {
    StringBuilder sql =
        new StringBuilder("SELECT new com.anhtq.app.admin.controller.booking.serviceresponse.");
    sql.append(
        "GetMyBookingServiceResponse(entity.id, service.image, service.name, "
            + "service.sortDescription, entity.status, entity.bookingDate, discount.code, "
            + "entityDetail.cost, entityDetail.promotionalPrice) ");
    sql.append(" FROM BookingEntity AS entity INNER JOIN BookingDetailEntity AS entityDetail ");
    sql.append(SPACE).append("ON entity.id = entityDetail.bookingId");
    sql.append(SPACE)
        .append("INNER JOIN ServiceEntity AS service ON service.id = entity.serviceId ");
    sql.append(SPACE).append("INNER JOIN UserEntity AS user ON user.id = entity.userId ");
    sql.append(SPACE)
        .append("LEFT JOIN CustomerEntity AS customer ON customer.id = entity.customerId");
    sql.append(SPACE)
        .append("LEFT JOIN DiscountEntity AS discount ON discount.id = entityDetail.discountId ");
    sql.append(SPACE).append("WHERE user.id = :id");
    sql.append(SPACE).append("ORDER BY entity.bookingDate DESC");
    Query query = entityManager.createQuery(sql.toString());
    query.setParameter("id", userId);
    List<GetMyBookingServiceResponse> list = query.getResultList();

    return list.stream()
        .map(
            e ->
                GetMyBookingServiceResponse.builder()
                    .id(e.getId())
                    .serviceImg(e.getServiceImg())
                    .serviceName(e.getServiceName())
                    .sortDescription(e.getSortDescription())
                    .serviceName(e.getServiceName())
                    .status(e.getStatus())
                    .bookingDate(e.getBookingDate())
                    .bookingDateString(formatDate(e.getBookingDate()))
                    .discountName(e.getDiscountName())
                    .cost(e.getCost())
                    .promotionalPrice(e.getPromotionalPrice())
                    .build())
        .toList();
  }

  private boolean validateCustomer(
      String email, String firstName, String lastName, String phoneNumber) {
    return Objects.isNull(email)
        || Objects.isNull(firstName)
        || Objects.isNull(lastName)
        || Objects.isNull(phoneNumber);
  }

  private LocalDateTime convertLongToLocalDateTime(Long time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
  }

  private String formatDate(LocalDateTime time) {
    return time.format(CUSTOM_FORMATTER);
  }
}
