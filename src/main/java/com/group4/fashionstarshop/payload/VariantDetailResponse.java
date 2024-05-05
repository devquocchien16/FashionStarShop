package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.dto.ImageDTO;
import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariantDetailResponse {
    private List<ImageDTO> imageDTOS;
    private List<OptionValueDTO> optionValueDTOS;
    private VariantDTO variantDto;
}
