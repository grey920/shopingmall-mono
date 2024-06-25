package com.sparta.shoppingmallmono.product.product.service.dto;

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
}
