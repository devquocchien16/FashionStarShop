package com.group4.fashionstarshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariantImageDTO {
	private Long id;    
    private String skuCode;
    private int stockQuantity;
    private double weight;
    private String name;
    private double price;
    private double salePrice;
    private String img;
    private ProductDTO productDTO;
    private List<OptionValueDTO> optionValueDTOList;
    private List<ImageConfirmDTO> imageDTOList;
}
