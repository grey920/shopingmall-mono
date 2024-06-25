package com.sparta.shoppingmallmono.product.brand.service;

import com.sparta.shoppingmallmono.product.brand.service.dto.BrandDTO;

import java.util.UUID;

public interface BrandService {
    BrandDTO createBrand( BrandDTO brandDTO );

    BrandDTO getBrand( UUID id );
}
