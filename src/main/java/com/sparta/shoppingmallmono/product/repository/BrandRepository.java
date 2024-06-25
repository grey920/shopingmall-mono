package com.sparta.shoppingmallmono.product.repository;

import com.sparta.shoppingmallmono.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository< Brand, UUID > {
}
