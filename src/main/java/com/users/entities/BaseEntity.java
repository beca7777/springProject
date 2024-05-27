package com.users.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Positive
    private Long createdAt;

    @Positive
    private Long updatedAt;

    @PrePersist
    protected void onPrePersist(){
        this.createdAt = Instant.now().toEpochMilli();
    }

    @PreUpdate
    protected void onPreUpdate(){
        this.updatedAt = Instant.now().toEpochMilli();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
