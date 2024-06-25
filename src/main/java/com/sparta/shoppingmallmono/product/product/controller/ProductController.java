package com.sparta.shoppingmallmono.product.product.controller;

import com.sparta.shoppingmallmono.product.product.controller.request.ProductRequest;
import com.sparta.shoppingmallmono.product.product.service.ProductService;
import com.sparta.shoppingmallmono.product.product.service.dto.ProductDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/products" )
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct( @RequestBody ProductRequest request ) {
        ProductDetailDTO productDetailDTO = productService.createProduct( request.toServiceDto() );
        return ResponseEntity.ok( productDetailDTO );
    }

    @GetMapping("/{id}" )
    public ResponseEntity<?> getProduct( @PathVariable("id") UUID id ) {
        ProductDetailDTO product = productService.getProduct( id );
        return ResponseEntity.ok( product );
    }


}
