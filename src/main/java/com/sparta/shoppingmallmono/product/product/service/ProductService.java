package com.sparta.shoppingmallmono.product.product.service;

import com.sparta.shoppingmallmono.product.product.service.dto.ProductDTO;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDetailDTO;

import java.util.UUID;

public interface ProductService {
    ProductDetailDTO createProduct( ProductDTO productDTO );

    ProductDetailDTO getProduct( UUID id );

}
