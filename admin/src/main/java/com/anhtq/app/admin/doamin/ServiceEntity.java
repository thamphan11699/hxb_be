package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_service")
public class ServiceEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_service_seq")
  @SequenceGenerator(name = "tbl_service_seq", sequenceName = "tbl_service_seq", allocationSize = 1)
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "sort_description")
  private String sortDescription;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "image")
  private String image;

  @Column(name = "delete_flg")
  private Boolean deleteFlg;

  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "price")
  private Long price;

  @Column(name = "introduction", columnDefinition = "TEXT")
  private String introduction;
}
