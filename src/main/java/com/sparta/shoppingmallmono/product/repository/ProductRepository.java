package com.sparta.shoppingmallmono.product.repository;

import com.sparta.shoppingmallmono.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository< Product, UUID > {
}
