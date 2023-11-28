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
@Table(schema = "public", name = "tbl_discount")
public class DiscountEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_discount_seq")
  @SequenceGenerator(
      name = "tbl_discount_seq",
      sequenceName = "tbl_discount_seq",
      allocationSize = 1)
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "percent")
  private Integer percent;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @Column(name = "minimum_order")
  private Long minimumOrder;

  @Column(name = "maximum_money")
  private Long maximumMoney;

  @Column(name = "delete_flg")
  private Boolean deleteFlg;
}
