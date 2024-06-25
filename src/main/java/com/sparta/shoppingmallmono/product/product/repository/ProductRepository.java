package com.sparta.shoppingmallmono.product.product.repository;

import com.sparta.shoppingmallmono.product.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository< Product, UUID > {
    @EntityGraph(attributePaths = {"stock", "brand"})
    @Query("SELECT p FROM Product p")
    Page<Product> findAllWithStockAndBrand( Pageable pageable);
}
