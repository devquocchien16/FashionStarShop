package com.group4.fashionstarshop.request;

import java.util.List;

import lombok.Data;

@Data
public class AddProductRequest {
	private String title;
	private String description;
	private String mainPicture;
	private Long categoryId;	
	private Long storeId;
	private List<AttributeRequest> attributes;
	
}
