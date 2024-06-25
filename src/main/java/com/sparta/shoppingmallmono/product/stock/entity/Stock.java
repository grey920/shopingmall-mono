package com.sparta.shoppingmallmono.product.stock.entity;

import com.sparta.shoppingmallmono.product.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock {
    @Id
    @GeneratedValue
    private UUID id;
    private int quantity;
    private int exposedQuantity;
    private int soldQuantity;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        product.setStock(this); // 양방향 설정
    }


    private void setExposedQuantity( int quantity ) {
        this.exposedQuantity = calcExposedQuantity(quantity);
    }

    /**
     * 재고 수량의 90%의 수량만 계산. (10%는 고객에게 보여주지 않는다.)
     * @return
     */
    public static int calcExposedQuantity(int quantity) {
        if ( quantity == 0 ) {
            return 0;
        }

        return (int) (quantity * 0.9);
    }
}
