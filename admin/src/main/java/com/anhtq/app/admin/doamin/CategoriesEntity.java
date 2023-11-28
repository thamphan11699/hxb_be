package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_categories")
public class CategoriesEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_categories_seq")
  @SequenceGenerator(
      name = "tbl_categories_seq",
      sequenceName = "tbl_categories_seq",
      allocationSize = 1)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "image")
  private String image;

  @Column(name = "is_home")
  private Boolean isHome;

  @Column(name = "delete_flg")
  private Boolean deleteFlg;
}
