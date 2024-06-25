package com.sparta.shoppingmallmono.product.brand.service.dto;

import com.sparta.shoppingmallmono.product.brand.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class BrandDTO {
    private UUID id;
    private String name;

    public static BrandDTO of( Brand brand ) {
        return BrandDTO.builder()
            .id( brand.getId() )
            .name( brand.getName() )
            .build();
    }
}
