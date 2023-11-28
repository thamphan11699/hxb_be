package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_color")
public class ColorEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_color_seq")
  @SequenceGenerator(name = "tbl_color_seq", sequenceName = "tbl_color_seq", allocationSize = 1)
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "delete_flg")
  private Boolean deleteFlg;
}
