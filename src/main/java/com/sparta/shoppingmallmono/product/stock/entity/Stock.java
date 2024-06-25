package com.sparta.shoppingmallmono.product.stock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
