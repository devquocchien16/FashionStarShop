package com.group4.fashionstarshop.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class VariantDTO {
    private Long id;    
    private String skuCode;
    private int stockQuantity;
    private double weight;
    private String name;
    private double price;
    private double salePrice;
    private String img;
    private Boolean isDeleted;
    private ProductDTO productDTO;   
    private List<OptionValueDTO> optionValueDTOList;
    private List<ImageDTO> imageDTOList;
    private List<ReviewDTO> reviewDTOList; 
     
    
}
