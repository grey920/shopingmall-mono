package com.sparta.shoppingmallmono.product.product.controller.request;

import com.sparta.shoppingmallmono.product.product.service.dto.ProductDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank
    private String title;
    private UUID brandId;
    private int price;
    private int discountRate;
    private String description;

    private String thumbnailImage;

    private List<String> detailImages;

    private int quantity;

    public ProductDTO toServiceDto() {
        return ProductDTO.builder()
                .title(title)
                .brandId(brandId)
                .price(price)
                .discountRate(discountRate)
                .description(description)
                .thumbnailImage(thumbnailImage)
                .detailImages(detailImages)
                .quantity(quantity)
                .build();
    }
}
