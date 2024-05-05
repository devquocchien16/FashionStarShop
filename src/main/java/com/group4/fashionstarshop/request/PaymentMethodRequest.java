package com.group4.fashionstarshop.request;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodRequest {
    Long id;
    Long userId;
    BigInteger cartNumber;
    String nameOnCard;
    String expirationDate;
    Boolean defaultPayment;
}
