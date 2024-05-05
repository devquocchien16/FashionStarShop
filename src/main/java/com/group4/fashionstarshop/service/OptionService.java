package com.group4.fashionstarshop.service;
import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.request.OptionRequest;

import java.util.List;

public interface OptionService {  
	List<OptionTableDTO> getOptionsByProductId(Long productId);
	OptionTableDTO createOption(OptionRequest optionRequest, Long productId);
	OptionTableDTO updateOption(OptionRequest optionRequest, Long option_id);
}
