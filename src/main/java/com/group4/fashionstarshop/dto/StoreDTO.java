package com.group4.fashionstarshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.group4.fashionstarshop.model.Address;

import jakarta.persistence.OneToMany;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Long id;
    private String name;   
    private String description; 
    private String editingName;
    private String logo;     
    private Boolean status;   
    private boolean type;      
    private String tax_num;
    private String certificate_image; 
    private String identity_type;
    private String identity_num;
    private String identity_image_1;
    private String identity_image_2;   
 
    private List<AddressDTO> addresses;     
    
    private String adminReply;
}
