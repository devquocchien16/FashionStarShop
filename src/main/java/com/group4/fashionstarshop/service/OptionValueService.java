package com.group4.fashionstarshop.service;
import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.model.OptionValue;
import com.group4.fashionstarshop.request.OptionValueRequest;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface OptionValueService {
    List<OptionValueDTO> getOptionValuesByVariantId(Long variant_id);  
    List<OptionValueDTO> getOptionValuesByOptionId(Long option_id);  
	OptionValueDTO createOptionValue(OptionValueRequest optionValueRequest, Long option_id);
	OptionValueDTO updateOptionValue(OptionValueRequest optionRequest, Long option_value_id);
	
}
