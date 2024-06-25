package com.sparta.shoppingmallmono.product.service.impl;

import com.sparta.shoppingmallmono.product.brand.service.BrandService;
import com.sparta.shoppingmallmono.product.brand.service.dto.BrandDTO;
import com.sparta.shoppingmallmono.product.product.service.ProductService;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDTO;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDetailDTO;
import com.sparta.shoppingmallmono.product.stock.entity.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BasicProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;


    @DisplayName( "상품 생성 테스트" )
    @Test
    void createProduct() {
        // given
        BrandDTO brandInfo = brandService.createBrand( getBrandDTO( "sample brand" ) );
        ProductDTO productDTO = getSampleProductDTO( brandInfo );

        // when
        ProductDetailDTO saved = productService.createProduct( productDTO );

        //then
        assertThat( saved ).isNotNull();
        assertThat( saved.getTitle() ).isEqualTo( productDTO.getTitle() );
        assertThat( saved.getExposedQuantity() ).isEqualTo( Stock.calcExposedQuantity( productDTO.getQuantity() ) );
    }


    @DisplayName( "상품 조회 테스트" )
    @Test
    void getProduct() {
        // given
        BrandDTO brandInfo = brandService.createBrand( getBrandDTO("sample brand") );
        ProductDTO productDTO = getSampleProductDTO( brandInfo );
        ProductDetailDTO saved = productService.createProduct( productDTO );

        // when
        ProductDetailDTO found = productService.getProduct( saved.getProductId() );

        // then
        assertThat( found ).isNotNull();
        assertThat( found.getTitle() ).isEqualTo( productDTO.getTitle() );
        assertThat( found.getExposedQuantity() ).isEqualTo( Stock.calcExposedQuantity( productDTO.getQuantity() ) );

        System.out.println( "found = " + found );
    }

    //    ======================================================================
    private static BrandDTO getBrandDTO( String name ) {
        return BrandDTO.builder().name( name ).build();
    }

    private static ProductDTO getSampleProductDTO( BrandDTO brandInfo ) {
        return ProductDTO.builder()
            .title( "title" )
            .brandId( brandInfo.getId() )
            .price( 5000 )
            .discountRate( 10 )
            .description( "description" )
            .thumbnailImage( "thumbnailImage" )
            .detailImages( null )
            .quantity( 1000 )
            .build();
    }


}