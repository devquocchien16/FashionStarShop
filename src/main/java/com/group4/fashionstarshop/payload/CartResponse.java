package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.dto.CartLineDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String message;
    private String statusCode;
    private List<CartLineDTO> data;
}
