package com.mini.joymall.sale.repository;

import com.mini.joymall.sale.entity.SalesProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesProductRepository extends JpaRepository<SalesProduct, Long> {
}
