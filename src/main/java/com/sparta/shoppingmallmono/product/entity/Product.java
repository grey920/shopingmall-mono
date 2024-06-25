package com.sparta.shoppingmallmono.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private int price;
    private UUID brandId;
    private String description;

    @Column(precision = 5, scale = 2)
    private double discountRate;

    private String thumbnailImage;

    @ElementCollection
    @CollectionTable(name = "product_detail_images", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> detailImages;

}
