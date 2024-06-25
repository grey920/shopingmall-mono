package com.sparta.shoppingmallmono.product.brand.entity;

import com.sparta.shoppingmallmono.product.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Brand {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "brand", orphanRemoval = true, cascade = CascadeType.ALL)
    private List< Product > productList;
}
