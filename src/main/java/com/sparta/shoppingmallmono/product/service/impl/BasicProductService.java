package com.sparta.shoppingmallmono.product.service.impl;

import com.sparta.shoppingmallmono.product.entity.Product;
import com.sparta.shoppingmallmono.product.entity.Stock;
import com.sparta.shoppingmallmono.product.repository.ProductRepository;
import com.sparta.shoppingmallmono.product.repository.StockRepository;
import com.sparta.shoppingmallmono.product.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicProductService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    /**
     * 상품 생성
     * @param productDTO
     */
    @Transactional
    public ProductDTO createProduct( ProductDTO productDTO ) {
        if ( productDTO == null ) {
            throw new IllegalArgumentException( "상품 정보가 없습니다." );
        }

        Product savedProduct = productRepository.save( productDTO.toProductEntity() );
        Stock savedStock = stockRepository.save( productDTO.toStockEntity() );

        return ProductDTO.builder()
                .title( savedProduct.getTitle() )
                .brandId( savedProduct.getBrandId() )
                .price( savedProduct.getPrice() )
                .discountRate( savedProduct.getDiscountRate() )
                .description( savedProduct.getDescription() )
                .thumbnailImage( savedProduct.getThumbnailImage() )
                .detailImages( savedProduct.getDetailImages() )
                .quantity( savedStock.getQuantity() )
                .build();
    }

}
