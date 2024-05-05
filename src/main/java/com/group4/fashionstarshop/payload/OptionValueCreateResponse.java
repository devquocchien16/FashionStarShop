package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.dto.OptionValueDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionValueCreateResponse {
    private List<OptionValueDTO> optionValueDtoList;
    private String message;
}
