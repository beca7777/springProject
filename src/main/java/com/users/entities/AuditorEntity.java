package com.users.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditorEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(length = 50,updatable = false, nullable = false)
    private String createdBy;


    @LastModifiedDate
    private LocalDateTime updatedOn;

    @LastModifiedBy
    private String updatedBy;
}
