package com.users.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends BaseEntity {

    private Instant deliveryDate;

    private Instant orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
