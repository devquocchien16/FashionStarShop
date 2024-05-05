package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.dto.VariantDTO;

import lombok.Data;
import org.springframework.data.domain.Page;
@Data
public class SearchResponse {
    private Page<VariantDTO> variantDtoPage;


}
