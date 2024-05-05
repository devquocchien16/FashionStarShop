package com.group4.fashionstarshop.request;

import com.group4.fashionstarshop.dto.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreCategoryResquest {	
	private String name;
	private Long category_id;	
}
