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
public class Order extends AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant deliveryDate;

    @Column(nullable = false)
    private Instant orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
