package com.sparta.shoppingmallmono.product.repository;

import com.sparta.shoppingmallmono.product.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository< Stock, UUID > {
}
