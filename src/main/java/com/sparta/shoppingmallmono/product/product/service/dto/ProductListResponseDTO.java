package com.sparta.shoppingmallmono.product.product.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductListResponseDTO {
    private List<ProductDTO> productList;
    private int totalPage;
    private int currentPage;
}
