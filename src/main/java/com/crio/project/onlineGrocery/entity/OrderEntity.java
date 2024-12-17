package com.crio.project.onlineGrocery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @CreationTimestamp
    private LocalDateTime orderDate;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(name="order_product",
            joinColumns = @JoinColumn(name="order_id"),
            inverseJoinColumns = @JoinColumn(name="product_id"))
    private List<ProductEntity> products;

}
