package com.group4.fashionstarshop.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDTO {
    private Long id;
    private BigInteger cartNumber;

    private String nameOnCard;

    private String expirationDate;

    private Boolean defaultPayment;
    
}
