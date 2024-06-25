package com.sparta.shoppingmallmono.product.brand.controller;

import com.sparta.shoppingmallmono.product.brand.controller.request.BrandRequest;
import com.sparta.shoppingmallmono.product.brand.service.BrandService;
import com.sparta.shoppingmallmono.product.brand.service.dto.BrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<?> createBrand( @RequestBody BrandRequest request ) {
        BrandDTO brandDTO = brandService.createBrand( request.toServiceDto() );
        return ResponseEntity.ok( brandDTO );
    }
}
