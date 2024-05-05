package com.group4.fashionstarshop.payload;

import com.group4.fashionstarshop.dto.StoreDTO;

import lombok.Data;

@Data
public class StoreResponse {
	 private StoreDTO storeDTO;
	 private String reason;	 
}
