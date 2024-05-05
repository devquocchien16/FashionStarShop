package com.group4.fashionstarshop.request;

import com.group4.fashionstarshop.dto.ProductAttributeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeRequest {
    List<ProductAttributeDTO> productAttributeDtoList;
}
