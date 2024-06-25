package com.sparta.shoppingmallmono.product.product.controller;

import com.sparta.shoppingmallmono.product.product.controller.request.ProductRequest;
import com.sparta.shoppingmallmono.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/products" )
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public void createProduct( @RequestBody ProductRequest request ) {
        productService.createProduct( request.toServiceDto() );
    }

}
