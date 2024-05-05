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
public class OptionController {
	@Autowired
    private OptionService optionService;    

    @PostMapping("/{product_id}/create")
    public ResponseEntity<List<OptionTableDTO>> createOptions(@RequestBody OptionRequest optionRequest, @PathVariable(name = "product_id") Long productId) {
        List<OptionTableDTO> createdOptions = (List<OptionTableDTO>) optionService.createOption(optionRequest, productId);
        return new ResponseEntity<>(createdOptions, HttpStatus.CREATED);
        
//       if (options != null) {
//          optionCreateResponse.setMessage("Option created successfully");
//          optionCreateResponse.setOptionTableDtoList(options);
//          return new ResponseEntity<>(optionCreateResponse, HttpStatus.CREATED);
//      } else {
//          optionCreateResponse.setMessage("Failed to create Option");
//          return new ResponseEntity<>(optionCreateResponse,HttpStatus.BAD_REQUEST);
//      }
    }
}
