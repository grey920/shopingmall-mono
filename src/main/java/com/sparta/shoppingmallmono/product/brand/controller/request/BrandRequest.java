package com.sparta.shoppingmallmono.product.brand.controller.request;

import com.sparta.shoppingmallmono.product.brand.service.dto.BrandDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    private String name;

    public BrandDTO toServiceDto() {
        return BrandDTO.builder()
                .name(name)
                .build();
    }
}
