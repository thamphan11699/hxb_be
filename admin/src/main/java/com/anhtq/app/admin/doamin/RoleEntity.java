package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_role")
public class RoleEntity extends CommonEntity {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_role_seq")
    @SequenceGenerator(name = "tbl_role_seq", sequenceName = "tbl_role_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "delete_flg")
    private Boolean deleteFlg;
}
