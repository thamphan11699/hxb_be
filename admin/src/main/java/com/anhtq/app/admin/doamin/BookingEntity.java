package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_booking")
public class BookingEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_booking_seq")
  @SequenceGenerator(name = "tbl_booking_seq", sequenceName = "tbl_booking_seq", allocationSize = 1)
  private Long id;

  @Column(name = "user_type", nullable = false)
  private String userType;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "service_id", nullable = false)
  private Long serviceId;

  /*
  01 -> create
  02 -> approval
  03 -> cancel
  04 -> done
   */
  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "booking_date", nullable = false)
  private LocalDateTime bookingDate;
}
