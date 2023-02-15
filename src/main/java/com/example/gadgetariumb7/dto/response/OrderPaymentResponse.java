package com.example.gadgetariumb7.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderPaymentResponse {

    private Long id;

    private String fullName;

    private int orderNumber;

    private int totalSum;

    private int countOfProduct;

    private int discount;

    private int totalDiscount;

    private String productName;

    public OrderPaymentResponse(Long id, String fullName, int orderNumber, int totalSum, int countOfProduct, int totalDiscount) {
        this.id = id;
        this.fullName = fullName;
        this.orderNumber = orderNumber;
        this.totalSum = totalSum;
        this.countOfProduct = countOfProduct;
        this.totalDiscount = totalDiscount;

    }
}
