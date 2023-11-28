package com.anhtq.app.admin.doamin;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonEntity {

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    protected LocalDateTime createDate;

    @CreatedBy
    @Column(name = "create_by", nullable = false)
    protected String createBy;

    @LastModifiedDate
    @Column(name = "update_date")
    protected LocalDateTime updateDate;

    @LastModifiedBy
    @Column(name = "update_by")
    protected String updateBy;
}
