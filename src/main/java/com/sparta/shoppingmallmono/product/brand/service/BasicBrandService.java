package com.sparta.shoppingmallmono.product.brand.service;

import com.sparta.shoppingmallmono.product.brand.entity.Brand;
import com.sparta.shoppingmallmono.product.brand.repository.BrandRepository;
import com.sparta.shoppingmallmono.product.brand.service.dto.BrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasicBrandService implements BrandService{
    private final BrandRepository brandRepository;

    @Transactional
    @Override
    public BrandDTO createBrand( BrandDTO dto ) {
        Brand saved = brandRepository.save( dto.toEntity() );
        return BrandDTO.of( saved );
    }

    @Override
    public BrandDTO getBrand( UUID id ) {
        return brandRepository.findById( id )
            .map( BrandDTO::of )
            .orElseThrow( () -> new IllegalArgumentException( "브랜드 정보가 없습니다." ) );
    }
}
