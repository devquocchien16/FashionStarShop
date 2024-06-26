package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.dto.*;
import lombok.Data;

import java.util.List;
@Data
public class ProductDetailResponse {
    private ProductDTO productDTO;
    private int ratings;
    private double star;
    private StoreDTO storeDto;
    private List<OptionTableDTO> optionTableDto;
    private List<VariantDTO> variantDTOList;
    private List<AttributeDTO> productAttributeDTOList;
    private VariantDTO variantDto;
    private List<ReviewDTO> reviewDTOList;
}
