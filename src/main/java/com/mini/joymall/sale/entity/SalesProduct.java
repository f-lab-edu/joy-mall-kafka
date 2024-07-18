package com.mini.joymall.sale.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "sales_product")
public class SalesProduct {

    @Id
    @Column(name = "sales_product_id")
    private Long id;

    private Long productOptionId;

    private long salesPrice;
    private long salesStock;

    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public long decreaseStock(long selectedQuantity) {
        long nowStock = this.salesStock - selectedQuantity;

        if (nowStock < 0) {
            throw new IllegalArgumentException("판매 수량이 부족합니다.");
        }

        this.salesStock = nowStock;
        return salesStock;
    }
}
