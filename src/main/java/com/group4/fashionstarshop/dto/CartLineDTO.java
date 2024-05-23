package com.group4.fashionstarshop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartLineDTO {
    @Null
    private Long id;
    private int quantity;
    private Long store_id;
    private CartDTO cartDto;
    private VariantDTO variantDto;
}
