package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartLineResponse {

    private Long id;

    private int quantity;

    private Cart cart;

    private Variant variant;
}
