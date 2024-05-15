package com.group4.fashionstarshop.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlutterOrderItem {
	String variantId;
	String price;
	String quantity;
	String orderId;
}
