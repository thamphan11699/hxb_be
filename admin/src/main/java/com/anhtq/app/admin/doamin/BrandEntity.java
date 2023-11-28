package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_brand")
public class BrandEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_brand_seq")
  @SequenceGenerator(name = "tbl_brand_seq", sequenceName = "tbl_brand_seq", allocationSize = 1)
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "delete_flg")
  private Boolean deleteFlg;

  @Column(name = "is_home")
  private Boolean isHome;

  @Column(name = "image")
  private String image;
}
