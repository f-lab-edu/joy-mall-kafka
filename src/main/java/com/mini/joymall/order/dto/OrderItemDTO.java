package com.mini.joymall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long salesProductId;
    private int quantity;
}

