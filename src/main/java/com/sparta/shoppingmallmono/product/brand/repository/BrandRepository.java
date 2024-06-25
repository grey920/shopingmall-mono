package com.sparta.shoppingmallmono.product.brand.repository;

import com.sparta.shoppingmallmono.product.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository< Brand, UUID > {
}
