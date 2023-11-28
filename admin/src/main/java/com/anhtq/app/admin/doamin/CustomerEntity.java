package com.anhtq.app.admin.doamin;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "public", name = "tbl_customer")
public class CustomerEntity extends CommonEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_customer_seq")
    @SequenceGenerator(name = "tbl_customer_seq", sequenceName = "tbl_customer_seq", allocationSize = 1)
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "phone_number", unique = true, nullable = false, length = 24)
    private String phoneNumber;
}
