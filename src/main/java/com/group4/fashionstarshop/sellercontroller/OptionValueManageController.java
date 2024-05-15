package com.group4.fashionstarshop.sellercontroller;

import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.payload.OptionValueCreateResponse;
import com.group4.fashionstarshop.request.OptionValueRequest;
import com.group4.fashionstarshop.service.OptionValueService;
import com.group4.fashionstarshop.service.implement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/seller/option-value")
public class OptionValueManageController {
	 @Autowired
    private OptionValueService optionValueService;	
   
	  @PostMapping("/{option_id}/create")
	    public ResponseEntity<OptionValueDTO> createOptionValues(@RequestBody OptionValueRequest optionValueRequest, @PathVariable(name="option_id") Long option_id) {
	        OptionValueDTO createdOptionValue = optionValueService.createOptionValue(optionValueRequest, option_id);
	        return new ResponseEntity<>(createdOptionValue, HttpStatus.CREATED);
	    }
	  
	  @GetMapping("/{option_id}/all")
	    public ResponseEntity<List<OptionValueDTO>> getValuesByOptionId(@PathVariable(name="option_id") Long option_id) {
		  List<OptionValueDTO> optionValueDTOs = optionValueService.getOptionValuesByOptionId(option_id);
	        return new ResponseEntity<>(optionValueDTOs, HttpStatus.CREATED);
	    }
	  
	  @PutMapping("/update/{value_id}")
	    public ResponseEntity<OptionValueDTO> updateOptionValue(@RequestBody OptionValueRequest request, @PathVariable(name="value_id") Long value_id) {
	        OptionValueDTO updatedOptionValue = optionValueService.updateOptionValue(request, value_id);
	        return new ResponseEntity<>(updatedOptionValue, HttpStatus.CREATED);
	    }
}
