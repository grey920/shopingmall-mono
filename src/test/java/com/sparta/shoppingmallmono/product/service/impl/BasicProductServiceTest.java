package com.sparta.shoppingmallmono.product.service.impl;

import com.sparta.shoppingmallmono.product.service.dto.ProductDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BasicProductServiceTest {
    @Autowired
    private BasicProductService basicProductService;

    @DisplayName("상품 생성 테스트")
    @Test
    void createProduct(){
        // given
        ProductDTO productDTO = ProductDTO.builder()
                .title("title")
                .brandId(null)
                .price(5000)
                .discountRate(10)
                .description("description")
                .thumbnailImage("thumbnailImage")
                .detailImages(null)
                .quantity(1000)
                .build();

        // when
        ProductDTO saved = basicProductService.createProduct( productDTO );

        //then
        assertThat( saved ).isNotNull();
    }
}