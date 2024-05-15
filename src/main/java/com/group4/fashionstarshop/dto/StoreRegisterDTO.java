package com.group4.fashionstarshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreRegisterDTO {
	 private Long id;
	 private String name;
	 private String logo;
	 private String edittingName;
	 private String tax_num;
	 private String certificate_image; 
	 private String identity_type;
	 private String identity_num;
	 private String identity_image_1;
	 private String identity_image_2;
	 private boolean status;
	 private String description;
	 private boolean type;
	 private String rejectedReason;
	 private SellerDTO sellerDTO;
}
