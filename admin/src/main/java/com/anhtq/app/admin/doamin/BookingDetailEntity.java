package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_booking_detail")
public class BookingDetailEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_booking_detail_seq")
  @SequenceGenerator(name = "tbl_booking_detail_seq", sequenceName = "tbl_booking_detail_seq", allocationSize = 1)
  private Long id;

  @Column(name = "booking_id", nullable = false)
  private Long bookingId;

  @Column(name = "discount_id")
  private Long discountId;

  @Column(name = "cost", nullable = false)
  private Long cost;

  @Column(name = "promotional_price", nullable = false)
  private Long promotionalPrice;
}
