package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.impl.OptionTableConverterImpl;
import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.model.OptionTable;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.repository.OptionTableRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.request.OptionRequest;
import com.group4.fashionstarshop.service.OptionService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {
	@Autowired
	private OptionTableRepository optionTableRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OptionTableConverterImpl optionTableConverter;

	@Override
	public OptionTableDTO createOption(OptionRequest optionRequest, Long productId) {
	    Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new EntityNotFoundException("Product not found")); 
	    
	        OptionTable optionTable = new OptionTable();
	        optionTable.setName(optionRequest.getOptionName());
	        optionTable.setProduct(product);	   
 
	    optionTableRepository.save(optionTable);
	    return optionTableConverter.entityToDTO(optionTable);
	}
	

	@Override
	public List<OptionTableDTO> getOptionsByProductId(Long productId) {
		 Product product = productRepository.findById(productId)
		            .orElseThrow(() -> new EntityNotFoundException("Product not found")); 
		 List<OptionTable> optionsOfProduct = optionTableRepository.findByProduct(product);
		    return optionTableConverter.entitiesToDTOs(optionsOfProduct);
	}



	@Override
	public OptionTableDTO updateOption(OptionRequest optionRequest, Long option_id) {
	    // Find the option by its ID
	    OptionTable optionTable = optionTableRepository.findById(option_id)
	            .orElseThrow(() -> new EntityNotFoundException("Option not found"));
	    
	   //for admin confirm
	   Product product = optionTable.getProduct();
	   product.setNeedcheck(true);
	   productRepository.save(product);
	   optionTable.setStatus(false);
	   
	    // Update the option name
	    optionTable.setName(optionRequest.getOptionName());
	    // Save the updated option
	    optionTableRepository.save(optionTable);
	    // Convert and return the updated option DTO
	    return optionTableConverter.entityToDTO(optionTable);
	}


}
