package com.sparta.shoppingmallmono.product.service.impl;

import com.sparta.shoppingmallmono.product.brand.service.BrandService;
import com.sparta.shoppingmallmono.product.brand.service.dto.BrandDTO;
import com.sparta.shoppingmallmono.product.product.service.ProductService;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDTO;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDetailDTO;
import com.sparta.shoppingmallmono.product.product.service.impl.BasicProductService;
import com.sparta.shoppingmallmono.product.stock.entity.Stock;
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
    @Autowired
    private BrandService brandService;



    @DisplayName("상품 생성 테스트")
    @Test
    void createProduct(){
        // given
        BrandDTO brandInfo = brandService.createBrand( "brandName" );

        ProductDTO productDTO = ProductDTO.builder()
                .title("title")
                .brandId(brandInfo.getId())
                .price(5000)
                .discountRate(10)
                .description("description")
                .thumbnailImage("thumbnailImage")
                .detailImages(null)
                .quantity(1000)
                .build();

        // when
        ProductDetailDTO saved = productService.createProduct( productDTO );

        //then
        assertThat( saved ).isNotNull();
        assertThat( saved.getTitle() ).isEqualTo( productDTO.getTitle() );
        assertThat( saved.getExposedQuantity() ).isEqualTo( Stock.calcExposedQuantity( productDTO.getQuantity() ) );
    }


}