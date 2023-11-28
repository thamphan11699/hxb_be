package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_user")
public class UserEntity extends CommonEntity {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_user_seq")
  @SequenceGenerator(name = "tbl_user_seq", sequenceName = "tbl_user_seq", allocationSize = 1)
  private Long id;

  @Column(name = "email", unique = true, nullable = false, length = 100)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(name = "phone_number", unique = true, nullable = false, length = 24)
  private String phoneNumber;

  @Column(name = "status")
  private String status;

  @Column(name = "avatar")
  private String avatar;
}
