package com.sparta.shoppingmallmono.product.product.entity;

import com.sparta.shoppingmallmono.product.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
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
    @Column(precision = 5)
    private int discountRate;

    private String description;
    private String thumbnailImage;
    @ElementCollection
    @CollectionTable(name = "product_detail_images", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> detailImages;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private UUID brandId;

}
