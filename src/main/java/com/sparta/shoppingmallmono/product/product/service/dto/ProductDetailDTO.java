package com.sparta.shoppingmallmono.product.product.service.dto;

import com.sparta.shoppingmallmono.product.brand.entity.Brand;
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
public class ProductDetailDTO {
    private UUID productId;
    private String title;
    private int price;
    private int discountRate;
    private String description;
    private String thumbnailImage;
    private List<String> detailImages;

    private UUID stockId;
    private int quantity;
    private int exposedQuantity;

    private UUID brandId;
    private String brandName;

    public static ProductDetailDTO of ( Product product, Brand  brand ) {
        return ProductDetailDTO.builder()
            .productId( product.getId() )
            .title( product.getTitle() )
            .price( product.getPrice() )
            .discountRate( product.getDiscountRate() )
            .description( product.getDescription() )
            .thumbnailImage( product.getThumbnailImage() )
            .detailImages( product.getDetailImages() )

            .stockId( product.getStock().getId() )
            .quantity( product.getStock().getQuantity() )
            .exposedQuantity( product.getStock().getExposedQuantity() )
            .brandId( brand.getId() )
            .brandName( brand.getName() )
            .build();
    }
}
