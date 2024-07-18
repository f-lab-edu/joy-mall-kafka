package com.mini.joymall.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "ORDER_ITEM_ID")
    private Long id;
    private Long salesProductId;
    private long quantity;
    private long pricePerItem;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
