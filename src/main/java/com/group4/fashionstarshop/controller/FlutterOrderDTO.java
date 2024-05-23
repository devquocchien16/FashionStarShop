package com.group4.fashionstarshop.controller;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlutterOrderDTO {
	String userId;
	String orderDate;
	String addressId;
	String orderTotal;
	String paymentMethodId;
}
