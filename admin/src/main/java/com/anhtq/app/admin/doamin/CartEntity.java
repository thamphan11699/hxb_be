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
@Table(schema = "public", name = "tbl_cart")
public class CartEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_cart_seq")
  @SequenceGenerator(name = "tbl_cart_seq", sequenceName = "tbl_cart_seq", allocationSize = 1)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_type")
  private String userType;

  @Column(name = "qty")
  private Integer qty;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "product_attr_id")
  private Long productAttrId;

  @Column(name = "added_on")
  private LocalDateTime addedOn;
}
