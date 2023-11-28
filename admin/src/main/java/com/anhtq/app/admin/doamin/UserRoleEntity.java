package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_user_role")
public class UserRoleEntity extends CommonEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_user_role_seq")
  @SequenceGenerator(name = "tbl_user_role_seq", sequenceName = "tbl_user_role_seq", allocationSize = 1)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "roleId")
  private Long roleId;
}
