package com.group4.fashionstarshop.request;

import java.util.List;

import com.group4.fashionstarshop.dto.AddressDTO;

import lombok.Data;

@Data
public class AddStoreRequest {	 
	    private String name;  
	    private String logo;  
	    private String description;   
	    private String pickup_city;
	    private String pickup_district;
	    private String pickup_ward;
	    private String pickup_street;
	    
	    private String tax_district;
	    private String tax_city;
	    private String tax_ward;
	    private String tax_street;
	    
	    
	     
	    private boolean type;      
	    private String tax_num;
	    private String certificate_image; 
	    
	    private String identity_type;
	    private String identity_num;
	    private String identity_image_1;
	    private String identity_image_2;   
	 
	    private List<AddressDTO> addresses;    
	    
	    
}
