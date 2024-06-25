package com.sparta.shoppingmallmono.product.product.service.impl;

import com.sparta.shoppingmallmono.product.brand.entity.Brand;
import com.sparta.shoppingmallmono.product.brand.repository.BrandRepository;
import com.sparta.shoppingmallmono.product.product.entity.Product;
import com.sparta.shoppingmallmono.product.product.service.ProductService;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDetailDTO;
import com.sparta.shoppingmallmono.product.stock.entity.Stock;
import com.sparta.shoppingmallmono.product.product.repository.ProductRepository;
import com.sparta.shoppingmallmono.product.stock.repository.StockRepository;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicProductService implements ProductService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final BrandRepository brandRepository;

    /**
     * 상품 생성
     * @param productDTO
     */
    @Transactional
    public ProductDetailDTO createProduct( ProductDTO productDTO ) {
        if ( productDTO == null ) {
            throw new IllegalArgumentException( "상품 정보가 없습니다." );
        }

        Brand brandInfo = brandRepository.findById( productDTO.getBrandId() )
            .orElseThrow( () -> new IllegalArgumentException( "브랜드 정보가 없습니다." ) );

        Product product = productDTO.toProductEntity();
        Stock stock = productDTO.toStockEntity();

        product.setStock( stock );

        Product savedProduct = productRepository.save( product );
        stockRepository.save( stock );

        return ProductDetailDTO.of( savedProduct, brandInfo );
    }

    @Override
    public ProductDetailDTO getProduct( UUID id ) {
        Product product = productRepository.findById( id )
            .orElseThrow( () -> new IllegalArgumentException( "상품 정보가 없습니다." ) );


        Brand brand = brandRepository.findById( product.getBrandId() )
            .orElseThrow( () -> new IllegalArgumentException( "브랜드 정보가 없습니다." ) );

        return ProductDetailDTO.of( product, brand );
    }


}
