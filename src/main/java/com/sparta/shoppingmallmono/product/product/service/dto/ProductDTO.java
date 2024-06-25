package com.sparta.shoppingmallmono.product.product.service.dto;

import com.sparta.shoppingmallmono.product.product.entity.Product;
import com.sparta.shoppingmallmono.product.stock.entity.Stock;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ProductDTO {

    @NotBlank
    private String title;
    @NotNull
    private UUID brandId;
    private int price;
    private int discountRate;
    private String description;

    private String thumbnailImage;

    private List<String> detailImages;

    private int quantity;

    public Product toProductEntity() {
        return Product.builder()
                .title(title)
                .brandId(brandId)
                .price(price)
                .discountRate(discountRate)
                .description(description)
                .thumbnailImage(thumbnailImage)
                .detailImages(detailImages)
                .build();
    }

    public Stock toStockEntity() {
        return Stock.builder()
                .quantity(quantity)
                .exposedQuantity( Stock.calcExposedQuantity( quantity ) )
                .build();
    }
}
