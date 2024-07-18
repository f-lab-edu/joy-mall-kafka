package com.mini.joymall.sale.service;

import com.mini.joymall.order.dto.OrderItemDTO;
import com.mini.joymall.sale.entity.SalesProduct;
import com.mini.joymall.sale.repository.SalesProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class SalesProductService {

    private final SalesProductRepository salesProductRepository;

    @Transactional
    public void decreaseStock(OrderItemDTO orderItemDTO) {
        SalesProduct salesProduct = salesProductRepository.findById(orderItemDTO.getSalesProductId())
                .orElseThrow(NoSuchElementException::new);
        salesProduct.decreaseStock(orderItemDTO.getQuantity());
        salesProductRepository.save(salesProduct);
    }
}
