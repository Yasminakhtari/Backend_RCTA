package com.ifacehub.tennis.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract  class Auditable<U> {
    @CreatedDate
    protected LocalDateTime createdOn;
    @CreatedBy
    protected U createdBy;
    @LastModifiedDate
    protected LocalDateTime updatedOn;
    @LastModifiedBy
    protected U updatedBy;
}
