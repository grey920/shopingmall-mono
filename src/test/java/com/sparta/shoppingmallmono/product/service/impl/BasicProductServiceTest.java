package com.sparta.shoppingmallmono.product.service.impl;

import com.sparta.shoppingmallmono.product.brand.service.BrandService;
import com.sparta.shoppingmallmono.product.product.service.ProductService;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDTO;
import com.sparta.shoppingmallmono.product.product.service.impl.BasicProductService;
import jakarta.persistence.Basic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BasicProductServiceTest {
    @Autowired
    private ProductService productService;

    @DisplayName("상품 생성 테스트")
    @Test
    void createProduct(){
        // given
        ProductDTO productDTO = ProductDTO.builder()
                .title("title")
                .brandId(UUID.randomUUID())
                .price(5000)
                .discountRate(10)
                .description("description")
                .thumbnailImage("thumbnailImage")
                .detailImages(null)
                .quantity(1000)
                .build();

        // when
        ProductDTO saved = productService.createProduct( productDTO );

        //then
        assertThat( saved ).isNotNull();
        assertThat( saved.getTitle() ).isEqualTo( productDTO.getTitle() );
    }


}