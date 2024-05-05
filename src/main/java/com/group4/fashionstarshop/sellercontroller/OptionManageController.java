package com.group4.fashionstarshop.sellercontroller;

import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.payload.OptionCreateResponse;
import com.group4.fashionstarshop.request.OptionRequest;
import com.group4.fashionstarshop.service.OptionService;
import com.group4.fashionstarshop.service.implement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/seller/options")
public class OptionManageController {
	@Autowired
    private OptionService optionService;    

    @PostMapping("/{product_id}/create")
    public ResponseEntity<OptionTableDTO> createOptions(@RequestBody OptionRequest optionRequest, @PathVariable(name = "product_id") Long productId) {
        OptionTableDTO createdOption = optionService.createOption(optionRequest, productId);
        return new ResponseEntity<>(createdOption, HttpStatus.CREATED);
    }
    
    @PutMapping("/{option_id}/update")
    public ResponseEntity<OptionTableDTO> updateOption(@RequestBody OptionRequest optionRequest, @PathVariable(name = "option_id") Long option_id) {
        OptionTableDTO updatedOption = optionService.updateOption(optionRequest, option_id);       
        return new ResponseEntity<>(updatedOption, HttpStatus.CREATED);
    }
}
